@file:Suppress("NOTHING_TO_INLINE")

package com.juh9870.utils

typealias MultiArray<T> = Array<MutableSet<T>?>

inline fun <T> MultiArray<T>.add(key: Int, item: T): Boolean {
    return ensureSet(key).add(item)
}

inline fun <T> MultiArray<T>.remove(key: Int, item: T): Boolean {
    return ensureSet(key).remove(item)
}

inline fun <T> MultiArray<T>.has(key: Int, item: T): Boolean {
    return this[key]?.contains(item) ?: false
}

inline fun <T> MultiArray<T>.ensureSet(key: Int): MutableSet<T> {
    return this[key] ?: HashSet<T>().also { this[key] = it }
}