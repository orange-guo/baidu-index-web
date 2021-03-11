package club.geek66.baidu.index.common

import arrow.core.extensions.list.foldable.fold
import arrow.core.extensions.monoid

/**
 *
 * @author: orange
 * @date: 2021/3/11
 * @time: 上午11:37
 * @copyright: Copyright 2021 by orange
 */
val decodePtbkEncodeData: (String, String) -> String = { ptbk, data ->
	ptbkCharMap(ptbk).let { charMap ->
		data.map(charMap::getValue)
	}.map(Char::toString).fold(String.monoid())
}

val ptbkCharMap: (String) -> Map<Char, Char> = { ptbk ->
	ptbk.run {
		subSequence(0, length / 2).zip(subSequence(length / 2, length))
	}.toTypedArray().toMap()
}