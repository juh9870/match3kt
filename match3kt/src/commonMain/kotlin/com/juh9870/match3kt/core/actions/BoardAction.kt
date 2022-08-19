package com.juh9870.match3kt.core.actions

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board
import com.juh9870.serialization.DeepCopyable

/**
 * Functional interface for board-related actions
 */
interface BoardAction<B : Board<C, T>, C : Cell<T>, T> : DeepCopyable {
    /**
     * Processing function
     *
     * @param board board, that we are applying actions to
     * @param affectedCells set of cells affected by all actions so far
     */
    fun run(board: B, affectedCells: LinkedHashSet<Int>)
}