package com.juh9870.match3kt.example

import com.juh9870.match3kt.core.Cell
import com.juh9870.serialization.BundleReader
import com.juh9870.serialization.BundleWriter
import com.juh9870.serialization.DeepCopyable
import com.juh9870.utils.SoftGenericWorkaround

class ExampleCell(override var neighbours: IntArray) : Cell<ExampleCellType> {
    override var type: ExampleCellType = ExampleCellType.EMPTY

    constructor(bundle: BundleReader) : this(bundle.readIntArray()) {
        type = bundle.readEnum(ExampleCellType.values())
    }

    override fun isEmpty(): Boolean {
        return type == ExampleCellType.EMPTY
    }

    override fun setEmpty() {
        type = ExampleCellType.EMPTY
    }

    override fun serialize(bundle: BundleWriter) {
        bundle.write(neighbours)
        bundle.write(type)
    }

    override fun deserialize(bundle: BundleReader) {
        neighbours = bundle.readIntArray()
        type = bundle.readEnum(ExampleCellType.values())
    }

    @SoftGenericWorkaround
    override fun rawDeepCopy(): DeepCopyable {
        return ExampleCell(neighbours.copyOf()).also { it.type = type }
    }

    override fun toString(): String {
        return type.toString()
    }
}