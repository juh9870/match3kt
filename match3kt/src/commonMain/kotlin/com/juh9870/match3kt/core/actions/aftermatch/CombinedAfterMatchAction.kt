package com.juh9870.match3kt.core.actions.aftermatch

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board
import com.juh9870.match3kt.core.matching.MatchGroup
import com.juh9870.serialization.DeepCopyable
import com.juh9870.utils.SoftGenericWorkaround

class CombinedAfterMatchAction<B : Board<C, T>, C : Cell<T>, T>(private val actions: Array<AfterMatchAction<B, C, T>>) : AfterMatchAction<B, C, T> {
    override fun processMatches(board: B, matches: List<MatchGroup<T>>, emptyCells: MutableSet<Int>, affectedCells: LinkedHashSet<Int>) {
        for (action in actions) {
            action.processMatches(board, matches, emptyCells, affectedCells)
        }
    }

    @SoftGenericWorkaround
    override fun rawDeepCopy(): DeepCopyable {
        return CombinedAfterMatchAction(actions)
    }

    companion object {
        fun <B : Board<C, T>, C : Cell<T>, T> from(vararg actions: AfterMatchAction<B, C, T>): CombinedAfterMatchAction<B, C, T> {
            return CombinedAfterMatchAction(arrayOf(*actions))
        }
    }
}