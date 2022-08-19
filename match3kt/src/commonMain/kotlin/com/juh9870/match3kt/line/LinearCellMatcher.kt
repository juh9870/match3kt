package com.juh9870.match3kt.line

import com.juh9870.match3kt.core.matching.CellMatcher

interface LinearCellMatcher<CellType> : CellMatcher<CellType> {
    /**
     * Checks if [data] can be used as an origin of the match
     *
     * Example use case is a "wildcard" type that can be matched to any other
     * type, but can't be used to start a match
     *
     * @param data data to check starting availability
     * @return if [data] can be used to initialize a match
     */
    fun canStartMatch(data: CellType): Boolean

    /**
     * Checks if this cell can be backtracked to with a meaningful results
     *
     * Example use case is a "wildcard" type that can be in a middle of two
     * groups
     *
     * If checking for backtrack possibility is about as expensive as checking
     * for a match, it's recommended to just make this method always return `true`
     * to make solver always backtrack
     *
     * @param data data to check backtracking viability
     * @return if [data] can be used to initialize a match
     */
    fun requiresBacktracking(data: CellType): Boolean
}