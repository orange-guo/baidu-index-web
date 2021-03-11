package club.geek66.baidu.index.baidu

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @author: orange
 * @date: 2021/3/11
 * @time: 下午2:37
 * @copyright: Copyright 2021 by orange
 */
@Serializable
data class BaiduResult<T>(
	val status: Int,
	val data: T,
)

@Serializable
data class SearchIndexResult(
	val uniqid: String,
	@SerialName("userIndexes")
	val indexes: List<Indexes>
)

@Serializable
data class Indexes(
	@SerialName("word")
	val keywords: List<Keyword>,
	val all: KeywordIndexes,
	val pc: KeywordIndexes,
	val wise: KeywordIndexes,
	val type: IndexRangeType
)

@Serializable
enum class IndexRangeType {
	@SerialName("day")
	DAY,

	@SerialName("week")
	WEEK
}

@Serializable
data class Keyword(
	val name: String,
	val wordType: Int
)

@Serializable
data class KeywordIndexes(
	val startDate: String,
	val endDate: String,
	val data: String
)