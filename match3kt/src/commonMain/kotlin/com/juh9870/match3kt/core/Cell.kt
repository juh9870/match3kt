package com.juh9870.match3kt.core

import com.juh9870.serialization.Serializable
import com.juh9870.utils.HardGenericWorkaround

interface Cell<Type> : Serializable {
    val neighbours: IntArray

    /**
     * Type should be effectively immutable, because it gets passed by
     * reference a lot in internals
     */
    var type: Type

    /**
     * Checks if this cell is considered empty
     */
    fun isEmpty(): Boolean

    /**
     * Makes the cell empty
     *
     * After calling this method, calls to [isEmpty] should return `true`
     * until state is changed externally
     */
    fun setEmpty()

    /**
     * Swaps this cell with other cell of the same type
     *
     * This function is not fully type-safe and is only here, so you can
     * override it, and you should use [swap] in your code instead.
     */
    @HardGenericWorkaround
    fun swapWith(other: Cell<Type>) {
        this.type = other.type.also { other.type = this.type }
    }
}

/**
 * Swaps contents of two functions of the same type
 */
@OptIn(HardGenericWorkaround::class)
infix fun <T : Cell<C>, C> T.swap(other: T) {
    swapWith(other)
}