package club.geek66.baidu.index

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
internal class OrderRouteTests : FunSpec({
	test("Order routes test") {
		withTestApplication({ module(testing = true) }) {
			handleRequest(HttpMethod.Get, "/api/v1/customers/1").apply {
				response.content shouldBe """{"id":"1","name":"4396"}"""
				response.status() shouldBe HttpStatusCode.OK
			}
		}
	}
})