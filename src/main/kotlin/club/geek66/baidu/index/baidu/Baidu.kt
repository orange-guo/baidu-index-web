package club.geek66.baidu.index.baidu

import arrow.syntax.function.invoke
import club.geek66.baidu.index.common.DEFAULT_HTTP_CLI
import club.geek66.baidu.index.common.DEFAULT_JSON
import club.geek66.baidu.index.common.decodePtbkEncodeData
import club.geek66.baidu.index.routes.IndexInDateRange
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
val userAgentMozilla: HttpRequestBuilder.() -> Unit = {
	userAgent("Mozilla/5.0 (X11; Linux x86_64)")
}

val headerWithAuth: HttpRequestBuilder.(String) -> Unit = { baiduUss ->
	cookie("BDUSS", baiduUss)
}

val getIndex: (String, String, String, String) -> (List<IndexInDateRange>) =
	{ token, keyword, startDate, endDate ->
		runBlocking {
			val response: BaiduResult<SearchIndexResult> =
				DEFAULT_HTTP_CLI.get<String>("https://index.baidu.com/api/SearchApi/index") {
					userAgentMozilla()
					headerWithAuth(token)
					parameter("area", "0")
					parameter("word", "0")
					parameter("startDate", startDate)
					parameter("endDate", endDate)
					parameter("word", listOf(listOf(Keyword(keyword, wordType = 1))).let(DEFAULT_JSON::encodeToString))
				}.let(DEFAULT_JSON::decodeFromString)

			val decode = (decodePtbkEncodeData)(exchangePtbk(token, response.data.uniqid))

			response.data.indexes.map {
				Triple(it.keywords.first().name, it.type, it.all)
			}.map {
				IndexInDateRange(
					keyword = it.first,
					dateInterval = it.second,
					startDate = it.third.startDate,
					endDate = it.third.endDate,
					indexes = decode(it.third.data).trim().split(",").toList().map(String::toInt)
				)
			}
		}
	}

val exchangePtbk: (String, String) -> String = { token, uniqid ->
	runBlocking {
		DEFAULT_HTTP_CLI.get<String>("http://index.baidu.com/Interface/ptbk") {
			userAgentMozilla()
			headerWithAuth(token)
			parameter("uniqid", uniqid)
		}.let {
			DEFAULT_JSON.decodeFromString<BaiduResult<String>>(it)
		}
	}.data
}