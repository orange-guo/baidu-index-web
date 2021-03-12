package club.geek66.baidu.index.routes

import club.geek66.baidu.index.baidu.DateInterval
import club.geek66.baidu.index.baidu.getIndex
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import kotlinx.serialization.Serializable

/**
 *
 * @author: orange
 * @date: 2021/3/10
 * @time: 上午10:51
 * @copyright: Copyright 2021 by orange
 */
fun Route.configureIndexRoutes() {
	route("/api/v1/indexes/{keyword}") {
		get {
			call.run {
				val startDate = parameters.getOrFail("startDate")
				val endDate = parameters.getOrFail("endDate")
				val keyword = parameters.getOrFail("keyword")
				val token = request.authorization()!!

				getIndex(token, keyword, startDate, endDate).let {
					call.respond(it)
				}
			}
		}
	}
}

@Serializable
data class IndexInDateRange(
	val keyword: String,
	val startDate: String,
	val endDate: String,
	val dateInterval: DateInterval,
	val indexes: List<Int>
)