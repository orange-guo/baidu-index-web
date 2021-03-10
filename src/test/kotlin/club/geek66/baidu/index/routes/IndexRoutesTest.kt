package club.geek66.baidu.index.routes

import club.geek66.baidu.index.module
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 *
 * @author: orange
 * @date: 2021/3/10
 * @time: 下午3:50
 * @copyright: Copyright 2021 by orange
 */
internal class IndexRoutesTest : FunSpec({

	test("index") {
		withTestApplication({ module(testing = false) }) {
			handleRequest(
				HttpMethod.Get,
				"/api/v1/indexes/range=DAY;keywords=鸡你太美;startDate=2012-01-01;endDate=2012-01-02"
			).apply {
				response.status() shouldBe HttpStatusCode.OK
				val range: KeywordsIndexRange = Json.decodeFromString(response.content!!)
				range.startDate shouldBe "2021-01-01"
			}
		}
	}

})