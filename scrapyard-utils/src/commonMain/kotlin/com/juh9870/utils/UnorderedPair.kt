package com.juh9870.utils

import kotlin.jvm.JvmField
import kotlin.math.max
import kotlin.math.min

data class UnorderedPair<T>(
    @JvmField val first: T,
    @JvmField val second: T
) {
    @Suppress("UNCHECKED_CAST")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UnorderedPair<*>) return false
        val up: UnorderedPair<T> = other as UnorderedPair<T>
        return (first == up.first && second == up.second) ||
                (first == up.second && second == up.first)
    }

    override fun hashCode(): Int {
        val hashFirst = first.hashCode()
        val hashSecond = second.hashCode()
        val maxHash = max(hashFirst, hashSecond)
        val minHash = min(hashFirst, hashSecond)
        return minHash * 31 + maxHash
    }
}