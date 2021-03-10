package club.geek66.baidu.index.common

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