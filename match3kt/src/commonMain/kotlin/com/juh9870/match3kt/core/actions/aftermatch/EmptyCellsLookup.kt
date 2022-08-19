package com.juh9870.match3kt.core.actions.aftermatch

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board
import com.juh9870.match3kt.core.matching.MatchGroup
import com.juh9870.serialization.DeepCopyable
import com.juh9870.utils.SoftGenericWorkaround

class EmptyCellsLookup<B : Board<C, T>, C : Cell<T>, T> : AfterMatchAction<B, C, T> {
    override fun processMatches(board: B, matches: List<MatchGroup<T>>, emptyCells: MutableSet<Int>, affectedCells: LinkedHashSet<Int>) {
        emptyCells.clear()
        for ((index, cell) in board.cells.withIndex()) {
            if (cell.isEmpty()) {
                emptyCells.add(index)
            }
        }
    }

    @SoftGenericWorkaround
    override fun rawDeepCopy(): DeepCopyable {
        return EmptyCellsLookup<B, C, T>()
    }
}