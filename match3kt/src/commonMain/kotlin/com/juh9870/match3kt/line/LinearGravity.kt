package com.juh9870.match3kt.line

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.actions.BoardAction
import com.juh9870.match3kt.core.actions.aftermatch.AfterMatchAction
import com.juh9870.match3kt.core.actions.gravity.GravityProperties
import com.juh9870.match3kt.core.board.Board
import com.juh9870.match3kt.core.matching.MatchGroup
import com.juh9870.serialization.DeepCopyable
import com.juh9870.serialization.deepCopy
import com.juh9870.utils.SoftGenericWorkaround
import kotlin.jvm.JvmStatic

/**
 * Linear gravity action, implements both [BoardAction] and [AfterMatchAction]
 */
class LinearGravityAction<B, C : Cell<T>, T> : BoardAction<B, C, T>, AfterMatchAction<B, C, T>, DeepCopyable where B : Board<C, T>, B : LinesBoard {
    private val lines: Array<IntArray>
    private val properties: GravityProperties<B, C, T>

    companion object {
        @JvmStatic
        inline fun <B, C : Cell<T>, T> process(
            board: B,
            lines: Array<IntArray>,
            affectedCells: LinkedHashSet<Int>,
            emptyCells: MutableSet<Int>? = null,
            canFall: (board: B, index: Int, cell: C) -> Boolean
        ) where B : Board<C, T>, B : LinesBoard {
            if (emptyCells != null && emptyCells.isEmpty()) return
            for (line in lines) {
                var target = -1
                // Iterate through the line in a reverse order
                for (i in (line.size - 1) downTo 0) {
                    val index = line[i]
                    val cell = board.cells[index]
                    // If no fall target, and we have an empty cell, remember its line position
                    if (target == -1 && cell.isEmpty()) {
                        target = i
                    }
                    // If we have a target, and face a non-empty cell, move cell content down
                    if (target != -1 && !cell.isEmpty() && canFall(board, index, cell)) {
                        val dropTarget = line[target]
                        board.swap(index, dropTarget)
                        if (emptyCells != null) {
                            emptyCells.add(index)
                            emptyCells.remove(dropTarget)
                        }
                        affectedCells.add(index)
                        affectedCells.add(dropTarget)
                        // Move target 1 tile back in the line
                        target--
                    }
                }
            }
        }
    }

    /**
     * Constructs gravity action via provided lines
     *
     * @param lines array of lines to run gravity along
     */
    constructor(lines: Array<Line>, properties: GravityProperties<B, C, T>) {
        this.lines = lines
        this.properties = properties
    }

    /**
     * Construct gravity action by extracting lines from a board
     *
     * @param board board to extract lines from
     * @param lines array of line indices. If index is negative, line will be reversed
     */
    constructor(board: B, lines: Iterable<Int>, properties: GravityProperties<B, C, T>) : this(Unit.run {
        val linesList = mutableListOf<Line>()
        for (id in lines) {
            val lineId: Int
            val reverse: Boolean
            if (id < 0) {
                lineId = -id
                reverse = true
            } else {
                lineId = id
                reverse = false
            }
            var line = board.lines[lineId]
            if (reverse) line = line.reversedArray()
            linesList.add(line)
        }
        linesList.toTypedArray()
    }, properties)

    override fun processMatches(board: B, matches: List<MatchGroup<T>>, emptyCells: MutableSet<Int>, affectedCells: LinkedHashSet<Int>) {
        return process(board, lines, affectedCells, emptyCells, this.properties::canFall)
    }

    override fun run(board: B, affectedCells: LinkedHashSet<Int>) {
        return process(board, lines, affectedCells, canFall = this.properties::canFall)
    }

    @SoftGenericWorkaround
    override fun rawDeepCopy(): DeepCopyable {
        return LinearGravityAction<B, C, T>(lines.deepCopy(), properties.deepCopy())
    }
}