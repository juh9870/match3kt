package com.juh9870.serialization.platformTools


expect class ByteArrayOutputStream

expect inline fun ByteArrayOutputStream.write(byte: Int)
expect inline fun ByteArrayOutputStream.write(bytes: ByteArray)
expect inline fun ByteArrayOutputStream.toByteArray(): ByteArray
expect inline fun newByteArrayOutputStream(): ByteArrayOutputStream