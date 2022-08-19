package com.juh9870.match3kt.core.matching

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board

/**
 * Interface for validating match groups
 */
interface MatchGroupValidator<B : Board<C, CellType>, C : Cell<CellType>, CellType> {
    /**
     * Initial validation of the group, done right after it's created
     *
     * Any matches that don't depend on connection to other groups should go
     * here
     *
     * Merged groups should not be validated at this step, and so passing them
     * to [match] will result in undetermined behavior
     *
     * @param match group to validate
     * @param board current game board
     * @return `true` if group should be processed further, or `false` if it
     * should be discarded immediately
     */
    fun initialValidation(match: MatchGroup<CellType>, board: B): Boolean

    /**
     * Indicates whenever final validation is required
     *
     * If value of `true` is returned, validation will be preformed, and
     * [finalValidation] will be called for every element
     *
     * If returned value is `false`, validation may or may not be performed,
     * depending on implementation details
     *
     * @return `true` if validation is required, or `false` if validation is
     * optional
     */
    fun requiresFinalValidation(): Boolean

    /**
     * Final validation, done before groups are returned from the matcher
     *
     * Any matches that depend on other groups should go here
     *
     * Merged groups will be validated at this step
     *
     * Groups that get passed here as a part of merged groups should not
     * be passed again as individual groups in the same match
     *
     * @param match group to validate
     * @param board current game board
     * @param groups all matched groups
     * @return if group should be included in the match
     */
    fun finalValidation(match: MatchGroup<CellType>, board: B, groups: List<MatchGroup<CellType>>): Boolean
}