package club.geek66.baidu.index.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 *
 * @author: orange
 * @date: 2021/3/8
 * @time: 下午3:59
 * @copyright: Copyright 2021 by orange
 */
fun Route.configureHelloRoutes() {
	get("/") {
		call.respondText("Hello")
	}
}