package com.juh9870.serialization

import com.juh9870.utils.SoftGenericWorkaround

/**
 * Interface for objects that can be deep-copied
 */
interface DeepCopyable {
    /**
     * Deep copy method
     *
     * Must ALWAYS return object of the same type as an implementing type, and
     * its subclasses must reimplement this method to return themselves
     *
     * Caller should expect all functionality of the clone to be identical to
     * the cloned one, while being completely independent
     *
     * This method is not properly generic to express its usage, so it's
     * recommended to use [deepCopy] extension method instead
     */
    @SoftGenericWorkaround
    fun rawDeepCopy(): DeepCopyable
}

@OptIn(SoftGenericWorkaround::class)
inline fun <reified T : DeepCopyable> T.deepCopy(): T = rawDeepCopy() as T

inline fun <reified T : DeepCopyable> Array<T>.deepCopy(): Array<T> {
    return Array(size) {
        this[it].deepCopy()
    }
}