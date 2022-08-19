package com.juh9870.utils

import kotlin.random.Random

@Suppress("MemberVisibilityCanBePrivate")
class RandomExtensions {
    companion object {
        /**
         * Returns an index from chances, the probability of each index is the
         * weight values in [chances]
         *
         * @param chances weight values
         */
        fun Random.chances(chances: FloatArray): Int {
            var sum = 0f
            for (element in chances) {
                sum += element
            }
            return this.chances(chances, sum)
        }

        /**
         * Slightly faster version of [RandomExtensions.chances] function, that relies on provided
         * sum of chances, instead of calculating it on every call
         *
         * @param chances weight values
         * @param chancesSum
         */
        fun Random.chances(chances: FloatArray, chancesSum: Float): Int {
            val value = nextFloat() * chancesSum
            var sum = 0f
            for (i in chances.indices) {
                sum += chances[i]
                if (value < sum) {
                    return i
                }
            }
            return -1
        }
    }
}