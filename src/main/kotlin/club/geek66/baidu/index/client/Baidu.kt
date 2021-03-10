package club.geek66.baidu.index.client

import club.geek66.baidu.index.common.DEFAULT_JSON
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

/**
 *
 * @author: orange
 * @date: 2021/3/10
 * @time: 下午4:13
 * @copyright: Copyright 2021 by orange
 */

const val BAIDU_USS =
	"ndyWnQxQkpqaS1LZGZDNktQb1BORE9VTUhXaUhyNXRRT2JUc01CMFFweX5JQnBnSVFBQUFBJCQAAAAAAAAAAAEAAAC-5SM2yta7-sbBu7XBywAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAL-T8l-~k~JfM"

const val URL_SEARCH_INDEX = "https://index.baidu.com/api/SearchApi/index"

const val URL_SEARCH_PTBK = "http://index.baidu.com/Interface/ptbk"

fun main() = runBlocking {
	HttpClient(CIO) {
		install(JsonFeature) {
			serializer = KotlinxSerializer(DEFAULT_JSON)
		}
		install(Logging) {
			level = LogLevel.ALL
		}
	}.use { client ->
		val response: BaiduResult<SearchIndexResult> =
			client.get<String>(URL_SEARCH_INDEX) {
				headers {
					userAgent("Mozilla/5.0 (X11; Linux x86_64)")
					cookie("BDUSS", BAIDU_USS)
				}
				parameter("area", "0")
				parameter("word", "0")
				parameter("startDate", "2021-01-01")
				parameter("endDate", "2021-01-03")
				parameter("word", """[[{"name":"蔡徐坤","wordType":1}]]""".trimIndent())
			}.let(DEFAULT_JSON::decodeFromString)

		val uniqid = response.data.uniqid

		val ptbk: BaiduResult<String> =client.get<String>(URL_SEARCH_PTBK) {
			headers {
				userAgent("Mozilla/5.0 (X11; Linux x86_64)")
				cookie("BDUSS", BAIDU_USS)
			}
			parameter("uniqid", uniqid)
		}.let(DEFAULT_JSON::decodeFromString)


		println("ptbk: $ptbk")
		val data = response.data.indexes.first().all.data
		println("data: $data")
	}
}

@Serializable
data class BaiduResult<T>(
	val status: Int,
	val data: T,
)

@Serializable
data class SearchIndexResult(
	val uniqid: String,
	@SerialName("userIndexes")
	val indexes: List<Indexes>
)

@Serializable
data class Indexes(
	@SerialName("word")
	val keywords: List<Keyword>,
	val all: KeywordIndexes,
	val pc: KeywordIndexes,
	val wise: KeywordIndexes,
	val type: IndexType
)

@Serializable
enum class IndexType {
	@SerialName("day")
	DAY,

	@SerialName("week")
	WEEK
}

@Serializable
data class Keyword(
	val name: String,
	val wordType: Int
)

@Serializable
data class KeywordIndexes(
	val startDate: String,
	val endDate: String,
	val data: String
)

// ptbk r+JYM4Xqo0CDp3164912.+,%-57380
// data pMC1pqMD3rpqM+1Yr