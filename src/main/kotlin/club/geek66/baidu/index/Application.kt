package club.geek66.baidu.index

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

/**
 *
 * @author: orange
 * @date: 2021/3/5
 * @time: 下午6:02
 * @copyright: Copyright 2021 by orange
 */
fun main() {
	embeddedServer(factory = Netty, port = 8080) {
		routing {
			get("/") {
				call.respondText("Hello")
			}
		}
	}.start(wait = true)
}