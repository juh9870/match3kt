package com.juh9870.match3kt.core.actions.aftermatch

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board
import com.juh9870.match3kt.core.matching.MatchGroup
import com.juh9870.serialization.DeepCopyable

/**
 * Functional interface for doing post-match actions
 */
interface AfterMatchAction<B : Board<C, T>, C : Cell<T>, T> : DeepCopyable {

    /**
     * Processing function
     *
     * @param board board, that we are applying actions to
     * @param matches matched groups
     * @param emptyCells set of empty cells
     * @param affectedCells set of cells affected by all actions so far
     */
    fun processMatches(board: B, matches: List<MatchGroup<T>>, emptyCells: MutableSet<Int>, affectedCells: LinkedHashSet<Int>)
}