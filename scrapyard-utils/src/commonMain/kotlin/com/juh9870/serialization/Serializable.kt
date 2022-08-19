package com.juh9870.serialization

/**
 * Objects that can be both serialized, deserialized and deep-copied
 *
 * Serialization is done in raw bytes order, so upon deserialization, object
 * must consume all bytes delegated to its storage
 *
 * Good practice for serializing objects is to store their schema version
 * first, allowing for easier time during version transition
 */
interface Serializable : SerializableOnly, DeserializableOnly, DeepCopyable