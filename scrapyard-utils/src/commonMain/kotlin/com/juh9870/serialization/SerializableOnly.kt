package com.juh9870.serialization

/**
 * Objects that can only be serialized, but not deserialized
 *
 * Mainly used for read-only objects, that can't be deserialized normally
 */
interface SerializableOnly {
    fun serialize(bundle: BundleWriter)
}