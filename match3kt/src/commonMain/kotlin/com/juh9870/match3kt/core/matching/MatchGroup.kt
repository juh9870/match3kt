package com.juh9870.match3kt.core.matching

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board
import com.juh9870.utils.SoftGenericWorkaround
import com.soywiz.kds.IntArrayList
import com.soywiz.kds.IntDeque

abstract class MatchGroup<T>(
    /**
     * Group type used for matching
     */
    var type: T,
    var index: Int
) {
    /**
     * Cells belonging to this group
     */
    abstract val cells: Collection<Int>

    override fun toString(): String {
        return "{index: $index, cells: $cells, type: $type;}"
    }

    /**
     * Helper method for displaying a board with only this group present on it
     */
    @OptIn(SoftGenericWorkaround::class)
    fun <B : Board<C, T>, C : Cell<T>> displayOnBoard(b: B): B {
        @Suppress("UNCHECKED_CAST") val board = b.rawDeepCopy() as B
        for (cell in board.cells) {
            cell.setEmpty()
        }
        for (i in cells) {
            board.cells[i].type = type
        }
        return board
    }
}

class SimpleMatchGroup<T>(override val cells: Collection<Int>, type: T, index: Int) : MatchGroup<T>(type, index)

class GrowingMatchGroup<T>(type: T, index: Int) : MatchGroup<T>(type, index) {
    private val items: IntDeque = IntDeque()
    override val cells: Collection<Int>
        get() = items

    fun add(cell: Int) {
        items.addLast(cell)
    }

    fun addToFront(cell: Int) {
        items.addFirst(cell)
    }
}

class MergedMatchGroup<T>(type: T, index: Int) : MatchGroup<T>(type, index) {
    private var cellsCache: Collection<Int>? = null
    private val groups: ArrayList<MatchGroup<out T>> = ArrayList()

    fun getGroups(): List<MatchGroup<out T>> = groups

    fun size() = groups.size
    fun add(group: MatchGroup<out T>) {
        if (groups.contains(group)) return
        groups.add(group)
        cellsCache = null
    }

    fun has(group: MatchGroup<T>): Boolean {
        return groups.contains(group)
    }

    fun recalculate() {
        val cells = LinkedHashSet<Int>()
        for (group in groups) {
            for (cell in group.cells) {
                cells.add(cell)
            }
        }
        this.cellsCache = cells
    }

    override val cells: Collection<Int>
        get() {
            if (cellsCache == null) recalculate()
            return cellsCache!!
        }
}

class UnrelatedMatchedGroup<T>(type: T, index: Int) : MatchGroup<T>(type, index) {
    private val groups: IntArrayList = IntArrayList()

    fun getGroups(): IntArrayList = groups

    fun add(group: Int) {
        if (groups.contains(group)) return
        groups.add(group)
    }

    fun has(group: Int): Boolean {
        return groups.contains(group)
    }

    override val cells: Collection<Int>
        get() = throw Exception("Can't get cells from unrelated matched group")
}