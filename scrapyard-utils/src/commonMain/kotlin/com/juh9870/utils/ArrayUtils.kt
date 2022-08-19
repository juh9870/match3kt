@file:Suppress("NOTHING_TO_INLINE")

package com.juh9870.utils

import com.soywiz.kds.IntArrayList

inline fun <reified T, reified R> Array<T>.arrayMap(transform: (T) -> R): Array<R> {
    return Array(size) {
        transform(this[it])
    }
}

fun Collection<Int>.toIntArray(): IntArray {
    val arr = IntArray(size)
    var last = 0
    for (i in this) {
        arr[last++] = i
    }
    return arr
}

inline fun IntArrayList.addIfNotExists(n: Int) {
    if (!contains(n)) add(n)
}