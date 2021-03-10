package club.geek66.baidu.index

import club.geek66.baidu.index.common.DEFAULT_JSON
import club.geek66.baidu.index.routes.configureIndexRoutes
import club.geek66.baidu.index.routes.configureWelcomeRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*

/**
 *
 * @author: orange
 * @date: 2021/3/5
 * @time: 下午6:02
 * @copyright: Copyright 2021 by orange
 */
fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
	install(ContentNegotiation) {
		json(DEFAULT_JSON)
	}
	routing {
		configureWelcomeRoutes()
		configureIndexRoutes()
	}
}