@file:Suppress("NOTHING_TO_INLINE")

package com.juh9870.match3kt.core

import com.juh9870.serialization.BundleReader
import com.juh9870.serialization.BundleWriter
import com.juh9870.serialization.DeepCopyable
import com.juh9870.serialization.Serializable
import com.juh9870.utils.SoftGenericWorkaround
import kotlin.random.Random

// Copied from Random.kt
/** Takes upper [bitCount] bits (0..32) from this number. */
internal inline fun Int.takeUpperBits(bitCount: Int): Int =
    this.ushr(32 - bitCount) and (-bitCount).shr(31)

/**
 * Code copied from standard kotlin library v1.7.10
 *
 * Implementation is fixed here to be consistent across future Kotlin release,
 * and to implement custom serialization
 *
 * Original code (as of v1.7.10): [kotlin.random.XorWowRandom]
 */
class SRandom internal constructor(
    private var x: Int,
    private var y: Int,
    private var z: Int,
    private var w: Int,
    private var v: Int,
    private var addend: Int
) : Random(), Serializable {

    constructor(seed: Int) : this(seed, seed.shr(31))
    constructor(seed: Long) : this(seed.toInt(), seed.shr(32).toInt())
    constructor(bundle: BundleReader) : this(bundle.readInt(), bundle.readInt(), bundle.readInt(), bundle.readInt(), bundle.readInt(), bundle.readInt())

    internal constructor(seed1: Int, seed2: Int) :
            this(seed1, seed2, 0, 0, seed1.inv(), (seed1 shl 10) xor (seed2 ushr 4))

    init {
        require((x or y or z or w or v) != 0) { "Initial state must have at least one non-zero element." }

        // some trivial seeds can produce several values with zeroes in upper bits, so we discard first 64
        repeat(64) { nextInt() }
    }

    override fun nextInt(): Int {
        // Equivalent to the xorxow algorithm
        // From Marsaglia, G. 2003. Xorshift RNGs. J. Statis. Soft. 8, 14, p. 5
        var t = x
        t = t xor (t ushr 2)
        x = y
        y = z
        z = w
        val v0 = v
        w = v0
        t = (t xor (t shl 1)) xor v0 xor (v0 shl 4)
        v = t
        addend += 362437
        return t + addend
    }

    override fun nextBits(bitCount: Int): Int =
        nextInt().takeUpperBits(bitCount)

    override fun serialize(bundle: BundleWriter) {
        bundle.write(x)
        bundle.write(y)
        bundle.write(z)
        bundle.write(w)
        bundle.write(v)
        bundle.write(addend)
    }

    override fun deserialize(bundle: BundleReader) {
        x = bundle.readInt()
        y = bundle.readInt()
        z = bundle.readInt()
        w = bundle.readInt()
        v = bundle.readInt()
        addend = bundle.readInt()
    }

    @SoftGenericWorkaround
    override fun rawDeepCopy(): DeepCopyable {
        return SRandom(x, y, z, w, v, addend)
    }
}
