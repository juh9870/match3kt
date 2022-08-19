package com.juh9870.serialization

import com.juh9870.serialization.BundleReader

/**
 * Objects that can only be deserialized, but not serialized
 */
interface DeserializableOnly {
    fun deserialize(bundle: BundleReader)
}