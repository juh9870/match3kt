@file:Suppress("NOTHING_TO_INLINE")

package com.juh9870.serialization.platformTools

actual typealias ByteArrayOutputStream = java.io.ByteArrayOutputStream;

actual inline fun ByteArrayOutputStream.write(byte: Int) {
    write(byte)
}

actual inline fun ByteArrayOutputStream.write(bytes: ByteArray) {
    write(bytes, 0, bytes.size)
}
actual inline fun ByteArrayOutputStream.toByteArray():ByteArray {
    return toByteArray()
}

actual inline fun newByteArrayOutputStream(): ByteArrayOutputStream {
    return ByteArrayOutputStream()
}