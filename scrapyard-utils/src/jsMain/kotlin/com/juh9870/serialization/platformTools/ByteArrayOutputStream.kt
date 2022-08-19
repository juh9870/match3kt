@file:Suppress("NOTHING_TO_INLINE")

package com.juh9870.serialization.platformTools

import com.juh9870.serialization.JsByteArray
import com.juh9870.serialization.createJsByteArray
import org.khronos.webgl.Int8Array

actual typealias ByteArrayOutputStream = JsByteArray

actual inline fun ByteArrayOutputStream.write(byte: Int) {
    push(byte.toByte())
}

actual inline fun ByteArrayOutputStream.write(bytes: ByteArray) {
    for (b in bytes) {
        push(b)
    }
}

actual inline fun ByteArrayOutputStream.toByteArray(): ByteArray {
    @Suppress("UNCHECKED_CAST", "CAST_NEVER_SUCCEEDS")
    return Int8Array(this as Array<Byte>) as ByteArray
}

actual inline fun newByteArrayOutputStream(): ByteArrayOutputStream {
    return createJsByteArray()
}