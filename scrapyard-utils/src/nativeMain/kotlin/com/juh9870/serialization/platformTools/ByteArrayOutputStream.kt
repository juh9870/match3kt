@file:Suppress("NOTHING_TO_INLINE")
@file:OptIn(PlatformSpecificFallback::class)

package com.juh9870.serialization.platformTools

import com.juh9870.utils.PlatformSpecificFallback

actual typealias ByteArrayOutputStream = ByteOutput

actual inline fun ByteArrayOutputStream.write(byte: Int) {
    write(byte)
}

actual inline fun ByteArrayOutputStream.write(bytes: ByteArray) {
    write(bytes)
}

actual inline fun ByteArrayOutputStream.toByteArray():ByteArray {
    return toByteArray()
}

actual inline fun newByteArrayOutputStream(): ByteArrayOutputStream {
    return ByteOutput(0)
}