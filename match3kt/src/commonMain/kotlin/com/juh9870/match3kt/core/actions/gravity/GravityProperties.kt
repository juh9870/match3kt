package com.juh9870.match3kt.core.actions.gravity

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board
import com.juh9870.serialization.DeepCopyable
import com.juh9870.utils.SoftGenericWorkaround

/**
 * Interface declaring some gravity properties
 */
interface GravityProperties<B : Board<C, T>, C : Cell<T>, T> : DeepCopyable {
    /**
     * Checks if given cell type can fall due to gravity
     *
     * @param board board which gravity is calculated for
     * @param index index of checked cell on the board
     * @param cell checked cell
     */
    fun canFall(board: B, index: Int, cell: C): Boolean

    class SimpleProperties<B : Board<C, T>, C : Cell<T>, T> : GravityProperties<B, C, T> {
        override fun canFall(board: B, index: Int, cell: C): Boolean {
            return true
        }

        @SoftGenericWorkaround
        override fun rawDeepCopy(): DeepCopyable {
            return SimpleProperties<B, C, T>()
        }
    }
}