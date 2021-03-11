package club.geek66.baidu.index.baidu

import arrow.syntax.function.invoke
import club.geek66.baidu.index.common.DEFAULT_HTTP_CLI
import club.geek66.baidu.index.common.DEFAULT_JSON
import club.geek66.baidu.index.common.decodePtbkEncodeData
import club.geek66.baidu.index.routes.IndexInDateRange
import club.geek66.baidu.index.routes.IndexInDateRanges
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

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

val headerWithUserAgent: HttpRequestBuilder.(String) -> Unit = { userAgent ->
	userAgent(userAgent)
}

val headerWithAuth: HttpRequestBuilder.(String) -> Unit = { baiduUss ->
	cookie("BDUSS", baiduUss)
}

val buildCommon: HttpRequestBuilder.() -> Unit = {
	headerWithUserAgent("Mozilla/5.0 (X11; Linux x86_64)")
	headerWithAuth(BAIDU_USS)
}

val getIndex: (Set<String>, String, String) -> (IndexInDateRanges) = { keywords, startDate, endDate ->
	runBlocking {
		val response: BaiduResult<SearchIndexResult> =
			DEFAULT_HTTP_CLI.get<String>(URL_SEARCH_INDEX) {
				buildCommon()
				parameter("area", "0")
				parameter("word", "0")
				parameter("startDate", startDate)
				parameter("endDate", endDate)
				parameter(
					"word",
					keywords.map { Keyword(name = it, wordType = 1) }.map(::listOf).let(DEFAULT_JSON::encodeToString)
				)
			}.let(DEFAULT_JSON::decodeFromString)

		val decode = (decodePtbkEncodeData)(exchangePtbk(response.data.uniqid))

		response.data.indexes.map {
			IndexInDateRange(
				keyword = it.keywords.first().name,
				indexes = decode(it.all.data).trim().split(",").toList().map(String::toInt)
			)
		}.let {
			IndexInDateRanges(
				startDate = startDate,
				endDate = endDate,
				rangeUnit = response.data.indexes.first().type,
				rangeLength = it.first().indexes.count(),
				ranges = it
			)
		}
	}
}

val exchangePtbk: (String) -> String = { uniqid ->
	runBlocking {
		DEFAULT_HTTP_CLI.get<String>(URL_SEARCH_PTBK) {
			buildCommon()
			parameter("uniqid", uniqid)
		}.let {
			DEFAULT_JSON.decodeFromString<BaiduResult<String>>(it)
		}
	}.data
}