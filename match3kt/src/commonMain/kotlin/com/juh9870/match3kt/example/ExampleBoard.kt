package com.juh9870.match3kt.example

import com.juh9870.match3kt.core.board.Board
import com.juh9870.match3kt.line.*
import com.juh9870.match3kt.utils.SquareGrid
import com.juh9870.serialization.BundleReader
import com.juh9870.serialization.BundleWriter
import com.juh9870.serialization.DeepCopyable
import com.juh9870.serialization.deepCopy
import com.juh9870.utils.SoftGenericWorkaround
import com.juh9870.utils.arrayMap
import kotlin.jvm.JvmField

class ExampleBoard : Board<ExampleCell, ExampleCellType>, LinesBoard {
    override var cells: Array<ExampleCell>

    override var lines: Array<Line>

    @JvmField
    var width: Int

    @JvmField
    var height: Int

    constructor(width: Int, height: Int) : this(width, height, null, null)
    constructor(width: Int, height: Int, cells: Array<ExampleCell>? = null, lines: Array<Line>? = null) {
        this.width = width
        this.height = height
        this.cells = cells ?: SquareGrid.createGrid(width, height) { ExampleCell(it) }
        this.lines = lines ?: SquareGrid.createLines(width, height)
    }

    constructor(bundle: BundleReader) : this(bundle.readInt(), bundle.readInt(), bundle.readSerializableArray { it -> ExampleCell(it) }, bundle.readLines())

    override fun serialize(bundle: BundleWriter) {
        bundle.write(width)
        bundle.write(height)
        bundle.write(cells)
        bundle.write(lines)
    }

    override fun deserialize(bundle: BundleReader) {
        width = bundle.readInt()
        height = bundle.readInt()
        cells = bundle.readSerializableArray { it -> ExampleCell(it) }
        lines = bundle.readLines()
    }

    @SoftGenericWorkaround
    override fun rawDeepCopy(): DeepCopyable {
        val board = ExampleBoard(width, height)
        board.cells = cells.arrayMap { it.deepCopy() }
        board.lines = lines.deepCopy()
        return board
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (y in 0 until height) {
            for (x in 0 until width) {
                sb.append(cells[x + y * width])
            }
            sb.append('\n')
        }
        return sb.toString()
    }

    companion object {
        /**
         * These functions are here for testing and demonstration purposes only, they are not optimized in any way
         */
        fun from(data: List<String>, width: Int? = null, minLineSize: Int? = null): ExampleBoard {
            val height = data.size
            val w = width ?: data.first().length
            val board = ExampleBoard(w, height, lines = SquareGrid.createLines(w, height, minLineSize ?: 3))
            val actualData = data.map {
                it.substring(0, w).trim().padEnd(w, '-')
            }.joinToString("") { it }
            for (cell in board.cells.indices) {
                board.cells[cell].type = ExampleCellType.fromChar(actualData[cell])
            }
            return board
        }

        fun from(data: String, width: Int, minLineSize: Int? = null): ExampleBoard {
            return from(data.chunked(width), width, minLineSize)
        }

        fun from(vararg data: String, width: Int? = null, minLineSize: Int? = null): ExampleBoard {
            return from(data.toList(), width, minLineSize)
        }
    }
}