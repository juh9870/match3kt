@file:Suppress("NOTHING_TO_INLINE")

package com.juh9870.match3kt.line

import com.juh9870.serialization.BundleReader
import com.juh9870.serialization.BundleWriter

/**
 * Type alias for IntArray to be used in Lines boards
 */
typealias Line = IntArray

/**
 * Extension method to write array of lines into a bundle
 */
inline fun BundleWriter.write(lines: Array<Line>) {
    write(lines.size)
    for (line in lines) {
        write(line)
    }
}

inline fun BundleReader.readLines(): Array<Line> {
    val length = readInt()
    return Array(length) {
        readIntArray()
    }
}

inline fun Array<Line>.deepCopy(): Array<Line> {
    return Array(size) {
        this[it].copyOf()
    }
}