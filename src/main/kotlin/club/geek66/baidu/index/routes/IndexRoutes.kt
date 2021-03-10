package club.geek66.baidu.index.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.Serializable

/**
 *
 * @author: orange
 * @date: 2021/3/10
 * @time: 上午10:51
 * @copyright: Copyright 2021 by orange
 */
fun Route.configureIndexRoutes() {
	route("/api/v1/indexes/range=DAY;keywords={keywords};startDate={startDate};endDate={endDate}") {
		get {
			val start = call.parameters["startDate"]
			val end = call.parameters["endDate"]
			val keywords = call.parameters["keywords"]
			call.respond(
				KeywordsIndexRange(
					startDate = "2021-01-01",
					endDate = "2021-01-3",
					rangeElementCount = 3,
					elements = listOf(
						KeywordIndexInRange(keyword = "蔡徐坤", indexes = listOf(4396, 4399, 3233)),
						KeywordIndexInRange(keyword = "鸡你太美", indexes = listOf(3233, 4399, 4396))
					)
				)
			)
		}
	}
}

@Serializable
data class KeywordsIndexRange(
	val startDate: String,
	val endDate: String,
	val rangeElementCount: Int,
	val elements: List<KeywordIndexInRange>
)

@Serializable
data class KeywordIndexInRange(
	val keyword: String,
	val indexes: List<Int>
)