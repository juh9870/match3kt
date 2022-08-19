package com.juh9870.serialization

import com.soywiz.kmem.*
import kotlin.jvm.JvmInline

@JvmInline
value class BundleWriter(val stream: ByteArrayBuilder = ByteArrayBuilder()) {

    private inline fun append(byte: Byte) {
        stream.append(byte)
    }

//    private inline fun append(int: Int) {
//        append(int.toByte())
//    }

    //region Primitives
    fun write(value: Byte) {
        append(value)
    }

    fun write(value: Short) {
        append((value.toInt()/* shr 0*/).toByte())
        append((value.toInt() shr 8).toByte())
    }

    fun write(value: Int) {
        append((value/* shr 0*/).toByte())
        append((value shr 8).toByte())
        append((value shr 16).toByte())
        append((value shr 24).toByte())
    }

    fun write(value: Long) {
        append((value/* shr 0*/).toByte())
        append((value shr 8).toByte())
        append((value shr 16).toByte())
        append((value shr 24).toByte())
        append((value shr 32).toByte())
        append((value shr 40).toByte())
        append((value shr 48).toByte())
        append((value shr 56).toByte())
    }

    inline fun write(value: Float) {
        stream.f32BE(value)
    }

    inline fun write(value: Double) {
        stream.f64BE(value)
    }

    fun write(value: Boolean) {
        write((if (value) 1 else 0).toByte())
    }

    fun write(value: Char) {
        write(value.code)
    }

    fun write(value: String) {
        val bytes = value.encodeToByteArray()
        write(bytes.size)
        stream.append(bytes)
    }

    fun write(value: Enum<*>) {
        write(value.ordinal)
    }
    //endregion

    //region Serializable
    fun <T : SerializableOnly> write(value: T) {
        value.serialize(this)
    }

    fun <T : SerializableOnly> write(value: Array<T>) {
        write(value.size)
        for (serializable in value) {
            serializable.serialize(this)
        }
    }
    //endregion

    //region Arrays
    fun write(value: ByteArray) {
        write(value.size)
        for (element in value) {
            write(element)
        }
    }

    fun write(value: ShortArray) {
        write(value.size)
        for (element in value) {
            write(element)
        }
    }

    fun write(value: IntArray) {
        write(value.size)
        for (element in value) {
            write(element)
        }
    }

    fun write(value: FloatArray) {
        write(value.size)
        for (element in value) {
            write(element)
        }
    }

    fun write(value: LongArray) {
        write(value.size)
        for (element in value) {
            write(element)
        }
    }

    fun write(value: DoubleArray) {
        write(value.size)
        for (element in value) {
            write(element)
        }
    }

    fun write(value: CharArray) {
        write(value.size)
        for (element in value) {
            write(element)
        }
    }

    fun write(value: BooleanArray) {
        write(value.size)
        for (element in value) {
            write(element)
        }
    }

    fun write(value: Array<Enum<*>>) {
        write(value.size)
        for (element in value) {
            write(element)
        }
    }
    //endregion
}