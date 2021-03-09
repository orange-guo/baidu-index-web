package club.geek66.baidu.index

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.http.*
import io.ktor.server.testing.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

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

fun `获取明年的时间，以ISO-LOCAL-DATE格式返回`(): String = TimeUnit.DAYS.sleep(360).let {
	LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
}