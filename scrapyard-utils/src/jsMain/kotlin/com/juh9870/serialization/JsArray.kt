package com.juh9870.serialization

interface JsArray<T> {
    fun push(value: T)
    fun pop(): T?

    companion object {
        fun <T> create(): JsArray<T> {
            return js("[]") as JsArray<T>
        }
    }
}
external class JsByteArray {
    fun push(value: Byte)

    fun pop(): Byte?
}

@Suppress("UnsafeCastFromDynamic")
fun createJsByteArray(): JsByteArray {
    return js("[]")
}