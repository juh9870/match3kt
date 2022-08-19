package com.juh9870.match3kt.core.matching

interface CellMatcher<CellType> {
    /**
     * Tries to match [current] cell type against [other] type
     *
     * Returned type may be different from [current] type, in such case new
     * type should be used as base for matching from now on
     *
     * Internal logic expects that resulting type can be used in place of
     * [current] and return non-null values if either old [current] or
     * [other] is passes as [other]
     *
     * Example:
     * ```
     * val original = // some type
     * val other = // some other type
     * val matched = cellMatcher.tryMatch(original, other)
     *
     * if(matched != null) {
     *     // Both calls should not fail if original match succeeded is not null
     *     assertNotNull( cellMatcher.tryMatch(matched, original) )
     *     assertNotNull( cellMatcher.tryMatch(matched, other)    )
     * }
     * ```
     *
     * @param current current state
     * @param other incoming type
     * @return Matched data, or null if match failed
     */
    fun tryMatch(current: CellType, other: CellType): CellType?
}