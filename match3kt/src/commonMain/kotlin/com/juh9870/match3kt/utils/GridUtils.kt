package com.juh9870.match3kt.utils

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board
import com.juh9870.match3kt.line.Line
import com.juh9870.match3kt.line.LinesBoard

class SquareGrid {
    companion object {
        fun createLines(width: Int, height: Int): Array<Line> {
            @Suppress("UNCHECKED_CAST") val lines = arrayOfNulls<Line>(width + height) as Array<Line>
            for (x in 0 until width) {
                lines[x] = IntArray(height) { y -> x + y * width }
            }
            for (y in 0 until height) {
                lines[y + width] = IntArray(width) { x -> x + y * width }
            }
            return lines
        }

        fun createLines(width: Int, height: Int, minLineSize: Int): Array<Line> {
            return createLines(width, height).filter { it.size >= minLineSize }.toTypedArray()
        }

        inline fun <reified T : Cell<*>> createGrid(
            width: Int,
            height: Int,
            cell: (IntArray) -> T
        ): Array<T> {
            return Array(width * height) {
                val x = it % width
                val y = it / width
                var found = 0
                val arr = IntArray(4)
                if (y != 0) arr[found++] = (it - width)
                if (y != height - 1) arr[found++] = (it + width)
                if (x != 0) arr[found++] = (it - 1)
                if (x != width - 1) arr[found++] = (it + 1)

                cell(arr.copyOf(found))
            }
        }

        fun <B> getVerticalLines(board: B, width: Int): Array<Line> where B : LinesBoard, B : Board<*, *> {
            val height = board.cells.size / width
            if (board.lines.size != width + height && height >= width) {
                return emptyArray()
            }
            return Array(width) {
                board.lines[it].copyOf()
            }
        }

        fun <B> getHorizontalLines(board: B, width: Int): Array<Line> where B : LinesBoard, B : Board<*, *> {
            val height = board.cells.size / width
            var offset = width
            if (board.lines.size != width + height) {
                if (width >= height)
                    return emptyArray()
                else offset = 0
            }
            return Array(height) {
                board.lines[it + offset].copyOf()
            }
        }
    }
}