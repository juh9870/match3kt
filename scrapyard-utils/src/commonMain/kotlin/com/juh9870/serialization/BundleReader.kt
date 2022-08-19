@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.juh9870.serialization

import com.soywiz.kmem.readF32BE
import com.soywiz.kmem.readF64BE
import com.soywiz.kmem.writeArrayBE
import kotlin.jvm.JvmField

class BundleReader(@JvmField val buffer: ByteArray) {
    @JvmField
    var offset = 0

    //region Primitives
    inline fun readByte(): Byte {
        return buffer[offset].also { offset++ }
    }

    inline fun readShort(): Short {
        offset += 2
        return ((buffer[offset - 1].toInt() and 0xff shl 8) or
                (buffer[offset - 2].toInt() and 0xff)).toShort()
    }

    fun readInt(): Int {
        offset += 4
        return (buffer[offset - 1].toInt() and 0xff shl 24) or
                (buffer[offset - 2].toInt() and 0xff shl 16) or
                (buffer[offset - 3].toInt() and 0xff shl 8) or
                (buffer[offset - 4].toInt() and 0xff)
    }

    fun readLong(): Long {
        offset += 8
        return (buffer[offset - 1].toLong() and 0xff shl 56) or
                (buffer[offset - 2].toLong() and 0xff shl 48) or
                (buffer[offset - 3].toLong() and 0xff shl 40) or
                (buffer[offset - 4].toLong() and 0xff shl 32) or
                (buffer[offset - 5].toLong() and 0xff shl 24) or
                (buffer[offset - 6].toLong() and 0xff shl 16) or
                (buffer[offset - 7].toLong() and 0xff shl 8) or
                (buffer[offset - 8].toLong() and 0xff)
    }

    inline fun readFloat(): Float {
        return buffer.readF32BE(offset).also { offset += 4 }
    }

    inline fun readDouble(): Double {
        return buffer.readF64BE(offset).also { offset += 8 }
    }

    inline fun readBoolean(): Boolean {
        return readByte() != 0.toByte()
    }

    inline fun readChar(): Char {
        return Char(readInt())
    }

    inline fun readString(): String {
        val length = readInt()
        return buffer.decodeToString(offset, offset + length, true).also { offset += length }
    }

    inline fun <T : Enum<T>> readEnum(enumValues: Array<T>): T {
        val ord = readInt()
        return enumValues[ord]
    }
    //endregion

    //region Serializable
    inline fun <reified T : DeserializableOnly> readSerializable(ctor: () -> T): T {
        return ctor().also { it.deserialize(this) }
    }

    // This does not make sense, just call the function directly!
//    inline fun <reified T : DeserializableOnly> readSerializable(ctor: (BundleReader) -> T): T {
//        return ctor(this)
//    }

    inline fun <reified T : DeserializableOnly> readSerializableArray(ctor: () -> T): Array<T> {
        val length = readInt()
        return Array(length) {
            ctor().also { it.deserialize(this) }
        }
    }

    inline fun <reified T> readSerializableArray(ctor: (BundleReader) -> T): Array<T> {
        val length = readInt()
        return Array(length) {
            ctor(this)
        }
    }
    //endregion

    //region Arrays
    inline fun readByteArray(): ByteArray {
        return ByteArray(readInt()) {
            readByte()
        }
    }

    inline fun readShortArray(): ShortArray {
        return ShortArray(readInt()) {
            readShort()
        }
    }

    inline fun readIntArray(): IntArray {
        return IntArray(readInt()) {
            readInt()
        }
    }

    inline fun readFloatArray(): FloatArray {
        return FloatArray(readInt()) {
            readFloat()
        }
    }

    inline fun readLongArray(): LongArray {
        return LongArray(readInt()) {
            readLong()
        }
    }

    inline fun readDoubleArray(): DoubleArray {
        return DoubleArray(readInt()) {
            readDouble()
        }
    }

    inline fun readCharArray(): CharArray {
        return CharArray(readInt()) {
            readChar()
        }
    }

    inline fun readBooleanArray(): BooleanArray {
        return BooleanArray(readInt()) {
            readBoolean()
        }
    }

    inline fun <reified T : Enum<T>> readEnumArray(enumValues: Array<T>): Array<T> {
        return Array(readInt()) {
            readEnum(enumValues)
        }
    }
    //endregion
}