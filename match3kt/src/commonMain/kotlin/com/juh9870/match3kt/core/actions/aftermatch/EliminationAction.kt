package com.juh9870.match3kt.core.actions.aftermatch

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board
import com.juh9870.match3kt.core.matching.MatchGroup
import com.juh9870.serialization.DeepCopyable
import com.juh9870.utils.SoftGenericWorkaround
import kotlin.jvm.JvmStatic

class EliminationAction<B : Board<C, T>, C : Cell<T>, T> : AfterMatchAction<B, C, T> {
    companion object {
        @JvmStatic
        fun <B : Board<C, T>, C : Cell<T>, T> process(board: B, matches: List<MatchGroup<T>>, emptyCells: MutableSet<Int>, affectedCells: LinkedHashSet<Int>) {
            if (matches.isEmpty()) return
            for (match in matches) {
                for (cell in match.cells) {
                    board.cells[cell].setEmpty()
                    emptyCells.add(cell)
                    affectedCells.add(cell)
                }
            }
        }
    }

    override fun processMatches(board: B, matches: List<MatchGroup<T>>, emptyCells: MutableSet<Int>, affectedCells: LinkedHashSet<Int>) {
        return process(board, matches, emptyCells, affectedCells)
    }

    @SoftGenericWorkaround
    override fun rawDeepCopy(): DeepCopyable {
        return EliminationAction<B, C, T>()
    }
}