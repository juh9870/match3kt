@file:Suppress("MemberVisibilityCanBePrivate")

package com.juh9870.match3kt.core.painter

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board
import com.juh9870.match3kt.line.LinesBoard

class BoardPainter<C : Cell<D>, D>(private val provider: RandomCellDataProvider<D>) {

    private var pen: D? = null

    fun Board<out C, D>.fillEmpty() = fillBoard(true)

    /**
     * Fills board with random tiles
     */
    fun Board<out C, D>.fillBoard(onlyEmpty: Boolean = false) {
        for (i in this.cells.indices) {
            val cell = this.cells[i]
            if (onlyEmpty && !cell.isEmpty()) continue
            set(i)
        }
    }

    fun Board<out C, D>.set(index: Int) {
        this.cells[index].type = pen ?: provider.getCellData()
    }

    fun <T> T.line(line: Int, from: Int, until: Int) where T : Board<out C, D>, T : LinesBoard {
        val cellsLine = this.lines[line]
        for (i in from until until) {
            set(cellsLine[i])
        }
    }

    fun pen(type: D) {
        pen = type
    }

    fun penRandom() {
        pen = null
    }
}