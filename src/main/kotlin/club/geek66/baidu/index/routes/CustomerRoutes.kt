package club.geek66.baidu.index.routes

import club.geek66.baidu.index.Customer
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 *
 * @author: orange
 * @date: 2021/3/8
 * @time: 上午11:19
 * @copyright: Copyright 2021 by orange
 */
fun Route.configureCustomerRoutes() {
	route("/api/v1/customers") {
		get("{id}") {
			call.respond(
				Customer(
					id = call.parameters["id"]!!,
					name = "4396"
				)
			)
		}
	}
}