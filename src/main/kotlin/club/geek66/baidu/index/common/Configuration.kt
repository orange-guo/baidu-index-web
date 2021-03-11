package club.geek66.baidu.index.common

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json

/**
 *
 * @author: orange
 * @date: 2021/3/10
 * @time: 下午6:05
 * @copyright: Copyright 2021 by orange
 */
val DEFAULT_JSON: Json = Json(Json) {
	ignoreUnknownKeys = true
}

val DEFAULT_HTTP_CLI = HttpClient(CIO) {
	install(JsonFeature) {
		serializer = KotlinxSerializer(DEFAULT_JSON)
	}
	install(Logging) {
		level = LogLevel.ALL
	}
}