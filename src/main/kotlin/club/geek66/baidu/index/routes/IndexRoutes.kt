package club.geek66.baidu.index.routes

import club.geek66.baidu.index.baidu.IndexRangeType
import club.geek66.baidu.index.baidu.getIndex
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
	route("/api/v1/indexes") {
		get {
			val startDate = call.parameters["startDate"]!!
			val endDate = call.parameters["endDate"]!!
			val keywords = call.parameters["keywords"]!!
			call.respond(
				getIndex(keywords.trim().split(",").toSet(), startDate, endDate)
			)
		}
	}
}

@Serializable
data class IndexInDateRanges(
	val startDate: String,
	val endDate: String,
	val rangeUnit: IndexRangeType,
	val rangeLength: Int,
	val ranges: List<IndexInDateRange>
)

@Serializable
data class IndexInDateRange(
	val keyword: String,
	val indexes: List<Int>
)