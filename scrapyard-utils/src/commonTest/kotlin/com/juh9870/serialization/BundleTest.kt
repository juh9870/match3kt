package com.juh9870.serialization

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.Exhaustive
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.ints
import kotlin.reflect.typeOf

private inline fun runTask(serialization: (BundleWriter) -> Unit, deserialization: (BundleReader) -> Unit, mismatchedBytesMessage: String = "Bytes weren't consumed fully") {
    val writer = BundleWriter()
    serialization(writer)
    val bytes = writer.stream.toByteArray()
    val reader = BundleReader(bytes)
    deserialization(reader)
    withClue(mismatchedBytesMessage) {
        reader.offset shouldBe bytes.size
    }
}

private inline fun <reified T> runSimpleTask(
    values: Array<T>,
    serialization: (BundleWriter, T) -> Unit,
    deserialization: (BundleReader) -> T,
    assertion: (T, T) -> Unit = { expected, actual ->
        actual shouldBe expected
    }
) {
    val writer = BundleWriter()
    for (value in values) {
        serialization(writer, value)
    }
    val bytes = writer.stream.toByteArray()
    val reader = BundleReader(bytes)
    for (value in values) {
        val deserialized = deserialization(reader)
        assertion(value, deserialized)
    }
}

private inline fun <reified T> runSimpleTask(
    value: T,
    serialization: (BundleWriter, T) -> Unit,
    deserialization: (BundleReader) -> T,
    assertion: (expected: T, actual: T) -> Unit = { expected, actual ->
        actual shouldBe expected
    }
) {
    runTask({
        serialization(it, value)
    }, {
        assertion(value, deserialization(it))
    }, "Bytes weren't consumed fully when testing for $value of type ${typeOf<T>()}")
}

class BundleTest : FunSpec({
//    context("Single values") {
    test("Byte tests") {
        checkAll(Exhaustive.ints(Byte.MIN_VALUE.toInt()..Byte.MAX_VALUE.toInt())) {
            runSimpleTask(it.toByte(), BundleWriter::write, BundleReader::readByte)
        }
    }

    test("Short tests") {
        checkAll(Exhaustive.ints(Short.MIN_VALUE.toInt()..Short.MAX_VALUE.toInt())) {
            runSimpleTask(it.toShort(), BundleWriter::write, BundleReader::readShort)
        }
    }

    test("Int tests") {
        checkAll<Int>(10000) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readInt)
        }
    }

    test("Long tests") {
        checkAll<Long>(10000) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readLong)
        }
    }

    test("Float tests") {
        checkAll<Float>(10000) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readFloat) { expected, actual ->
                run {
                    if(expected.isNaN())
                        expected.isNaN() shouldBe actual.isNaN()
                    else
                        expected.toRawBits() shouldBe actual.toRawBits()
                }
            }
        }
    }

    test("Double tests") {
        checkAll<Double>(10000) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readDouble) { expected, actual ->
                run {
                    if(expected.isNaN())
                        expected.isNaN() shouldBe actual.isNaN()
                    else
                        expected shouldBe actual
                }
            }
        }
    }

    test("Char tests") {
        checkAll(Exhaustive.ints(0..Char.MAX_VALUE.code)) {
            runSimpleTask(Char(it), BundleWriter::write, BundleReader::readChar)
        }
    }

    test("Boolean tests") {
        checkAll<Boolean> {
            runSimpleTask(it, BundleWriter::write, BundleReader::readBoolean)
        }
    }

    test("String tests") {
        checkAll<String>(10000) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readString)
        }
    }
//    }

//    context("Arrays") {
    test("Byte arrays") {
        checkAll(10, Arb.byteArray(Exhaustive.ints(0..5000), Arb.byte())) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readByteArray)
        }
    }

    test("Short arrays") {
        checkAll(10, Arb.shortArray(Exhaustive.ints(0..5000), Arb.short())) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readShortArray)
        }
    }

    test("Int arrays") {
        checkAll(10, Arb.intArray(Arb.int(0..5000), Arb.int())) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readIntArray)
        }
    }

    test("Long arrays") {
        checkAll(10, Arb.longArray(Arb.int(0..5000), Arb.long())) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readLongArray)
        }
    }

    test("Float arrays") {
        checkAll(10, Arb.floatArray(Arb.int(0..5000), Arb.float())) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readFloatArray)
        }
    }

    test("Double arrays") {
        checkAll(10, Arb.doubleArray(Arb.int(0..5000), Arb.double())) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readDoubleArray)
        }
    }

    test("Char arrays") {
        checkAll(10, Arb.charArray(Arb.int(0..5000), Arb.char())) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readCharArray)
        }
    }

    test("Boolean arrays") {
        checkAll(10, Arb.booleanArray(Arb.int(0..5000), Arb.boolean())) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readBooleanArray)
        }
    }

    test("String arrays") {
        val gen = Arb.string()
        checkAll(10, Arb.int(0..5000).flatMap { length -> Arb.list(gen, length..length) }.map { it.toTypedArray() }) {
            runSimpleTask(it, BundleWriter::write, BundleReader::readString)
        }
    }
//    }
})