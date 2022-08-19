package com.juh9870.serialization.platformTools

import com.juh9870.utils.PlatformSpecificFallback
import kotlin.jvm.JvmOverloads
import kotlin.jvm.Synchronized

/**
 * Restricted multiplatform implementation of JVM ByteArrayOutputStream
 * Code is directly copied from JVM sources with slight changes to adapt for
 * kotlin multiplatform
 *
 * This class is only to be used as a fallback value for JS/Native platforms,
 * please use [ByteArrayOutputStream] instead
 */
//@PlatformSpecificFallback
class ByteOutput @JvmOverloads constructor(size: Int = 32) {
    /**
     * The buffer where data is stored.
     */
    private var buf: ByteArray

    /**
     * The number of valid bytes in the buffer.
     */
    private var count = 0

    /**
     * Increases the capacity if necessary to ensure that it can hold
     * at least the number of elements specified by the minimum
     * capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     * @throws OutOfMemoryError if `minCapacity < 0`.  This is
     * interpreted as a request for the unsatisfiably large capacity
     * `(long) Integer.MAX_VALUE + (minCapacity - Integer.MAX_VALUE)`.
     */
    private fun ensureCapacity(minCapacity: Int) {
        // overflow-conscious code
        if (minCapacity - buf.size > 0) grow(minCapacity)
    }
    /**
     * Creates a new byte array output stream, with a buffer capacity of
     * the specified size, in bytes.
     *
     * @param   size   the initial size.
     * @exception  IllegalArgumentException if size is negative.
     */
    /**
     * Creates a new byte array output stream. The buffer capacity is
     * initially 32 bytes, though its size increases if necessary.
     */
    init {
        if (size < 0) {
            throw IllegalArgumentException(
                "Negative initial size: "
                        + size
            )
        }
        buf = ByteArray(size)
    }

    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private fun grow(minCapacity: Int) {
        // overflow-conscious code
        val oldCapacity = buf.size
        var newCapacity = oldCapacity shl 1
        if (newCapacity - minCapacity < 0) newCapacity = minCapacity
        if (newCapacity - MAX_ARRAY_SIZE > 0) newCapacity = hugeCapacity(minCapacity)
        buf = buf.copyOf(newCapacity)
    }

    /**
     * Writes the specified byte to this byte array output stream.
     *
     * @param   b   the byte to be written.
     */
    @Synchronized
    fun write(b: Int) {
        ensureCapacity(count + 1)
        buf[count] = b.toByte()
        count += 1
    }

    /**
     * Writes all bytes from the specified byte array to this byte
     * array output stream.
     *
     * @param   bytes   the data.
     */
    fun write(bytes: ByteArray) {
        write(bytes, 0, bytes.size)
    }

    /**
     * Writes `len` bytes from the specified byte array
     * starting at offset `off` to this byte array output stream.
     *
     * @param   b     the data.
     * @param   off   the start offset in the data.
     * @param   len   the number of bytes to write.
     */
    @Synchronized
    fun write(b: ByteArray, off: Int, len: Int) {
        if (((off < 0) || (off > b.size) || (len < 0) ||
                    ((off + len) - b.size > 0))
        ) {
            throw IndexOutOfBoundsException()
        }
        ensureCapacity(count + len)
        b.copyInto(buf, count, off, len)
        count += len
    }

    /**
     * Resets the `count` field of this byte array output
     * stream to zero, so that all currently accumulated output in the
     * output stream is discarded. The output stream can be used again,
     * reusing the already allocated buffer space.
     */
    @Synchronized
    fun reset() {
        count = 0
    }

    /**
     * Creates a newly allocated byte array. Its size is the current
     * size of this output stream and the valid contents of the buffer
     * have been copied into it.
     *
     * @return  the current contents of this output stream, as a byte array.
     */
    @Synchronized
    fun toByteArray(): ByteArray {
        return buf.copyOf(count)
    }

    /**
     * Returns the current size of the buffer.
     *
     * @return  the value of the `count` field, which is the number
     * of valid bytes in this output stream.
     */
    @Synchronized
    fun size(): Int {
        return count
    }

    /**
     * Converts the buffer's contents into a string decoding bytes using the
     * platform's default character set. The length of the new <tt>String</tt>
     * is a function of the character set, and hence may not be equal to the
     * size of the buffer.
     *
     *
     *  This method always replaces malformed-input and unmappable-character
     * sequences with the default replacement string for the platform's
     * default character set.
     *
     * @return String decoded from the buffer's contents.
     */
    @Synchronized
    override fun toString(): String {
        return buf.decodeToString(0, count, false)
    }

    companion object {
        private const val MAX_ARRAY_SIZE = Int.MAX_VALUE - 8
        private fun hugeCapacity(minCapacity: Int): Int {
            if (minCapacity < 0) throw OutOfMemoryError()
            return if ((minCapacity > MAX_ARRAY_SIZE)) Int.MAX_VALUE else MAX_ARRAY_SIZE
        }
    }
}

class OutOfMemoryError : Error("Out of memory")
