package com.juh9870.match3kt.core.matching

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board

/**
 * Base class for matching cells on boards
 */
abstract class Matcher<B : Board<C, CellType>, C : Cell<CellType>, CellType> {
    /**
     * Runs matching on a [board]
     *
     * @param board board to find matches on
     * @return list of matched groups
     */
    abstract fun findMatches(board: B): List<MatchGroup<CellType>>
}