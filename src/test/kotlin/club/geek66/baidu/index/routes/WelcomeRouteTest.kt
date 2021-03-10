package club.geek66.baidu.index.routes

import club.geek66.baidu.index.module
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.http.*
import io.ktor.server.testing.*

/**
 *
 * @author: orange
 * @date: 2021/3/8
 * @time: 下午4:45
 * @copyright: Copyright 2021 by orange
 */
internal class WelcomeRouteTest : FunSpec({
	test("Welcome route test") {
		withTestApplication({ module(testing = true) }) {
			handleRequest(HttpMethod.Get, "/welcome")
				.apply {
					response.status() shouldBe HttpStatusCode.OK
					response.contentType() shouldBe ContentType.parse("text/plain; charset=UTF-8")
					response.content shouldBe "Hello"
				}
		}
	}
})