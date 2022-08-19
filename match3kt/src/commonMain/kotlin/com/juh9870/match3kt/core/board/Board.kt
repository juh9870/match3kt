package com.juh9870.match3kt.core.board

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.swap
import com.juh9870.serialization.Serializable

/**
 * Class representing game board
 *
 * Board consists of cells that are stored in fully deterministic way and can
 * be accessed via an integer index
 */
abstract class Board<C : Cell<CellType>, CellType> : Serializable {
    /**
     * Board cells
     */
    abstract var cells: Array<C>

    /**
     * Swaps
     */
    fun swap(a: Int, b: Int) {
        cells[a] swap cells[b]
    }
}