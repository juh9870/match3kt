@file:Suppress("ReplaceSizeZeroCheckWithIsEmpty", "NOTHING_TO_INLINE")

package com.juh9870.match3kt.line

import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.board.Board
import com.juh9870.match3kt.core.matching.*
import com.juh9870.utils.addIfNotExists
import com.soywiz.kds.IntArrayList
import kotlin.jvm.JvmField

private const val DEBUG = false

/**
 * Matcher for [LinesBoard] boards
 *
 * @param matcher cell matcher
 * @param validator validator for matched groups
 * @param mergeNeighbours if matching neighboring groups should be combined
 */
class LineMatcher<B, C : Cell<CellType>, CellType>(
    @JvmField
    var matcher: LinearCellMatcher<CellType>,
    @JvmField
    var validator: MatchGroupValidator<B, C, CellType>,

    /**
     * Determines if matching neighboring groups should be combined
     */
    @JvmField
    var mergeNeighbours: Boolean,

    /**
     * Expected amount of match groups
     *
     * Changing this variable does not affect resulting matches, and is only
     * used for minor performance improvements
     *
     * I might've as well hardcoded this variable and performance impact would
     * be negligible, but I will keep it as a parameter
     *
     * Set to 0 to make lists start empty
     */
    @JvmField
    var expectedGroupsCount: Int = 2
) :
    Matcher<B, C, CellType>() where B : Board<C, CellType>, B : LinesBoard {
    override fun findMatches(board: B): List<MatchGroup<CellType>> {
        /**
         * Groups list is used to keep references to all match groups
         * The same group may appear under multiple indices as the result of
         * group merging
         */
        val groups = ArrayList<MatchGroup<CellType>>(expectedGroupsCount)

        /**
         * Array of the latest matched group id for given board index
         *
         * -1 is a default value and means that there is no group that
         * includes given index
         *
         * This assumes that only single group can occupy any given cell at
         * once, and that requires some special handling for wildcards
         */
        val positionedGroups = IntArray(board.cells.size) { -1 }

        var requiresFiltering = false

        for (line in board.lines) {
            var curGroup: GrowingMatchGroup<CellType>? = null

            // IntArrayList is used here instead of LinkedHashSet because
            // testing showed that on our scale it's slightly faster for small
            // boards, and comparable on larger boards
            //
            // IntSet performance is trash btw, around 20% lower than default
            // LinkedHashSet<int>, probably due to iterating
            val mergeCandidates = IntArrayList()

            for ((lineIndex, index) in line.withIndex()) {
                // Starting matching from the cell
                val cell = board.cells[index]

                if (curGroup != null) {
                    // Group already exists, so we try to continue it
                    val match = matcher.tryMatch(curGroup.type, cell.type)
                    if (match == null) {
                        // close the group
                        if (closeGroup(curGroup, groups, positionedGroups, mergeCandidates, board)) requiresFiltering = true
                        curGroup = null
                        mergeCandidates.clear()
                    } else {
                        addMatch(curGroup, index, match, positionedGroups, mergeCandidates)
                    }
                }
                if (curGroup == null) {
                    curGroup = newGroup(index, lineIndex, line, cell, positionedGroups, mergeCandidates, board)
                }
            }
            if (curGroup != null) if (closeGroup(curGroup, groups, positionedGroups, mergeCandidates, board)) requiresFiltering = true
        }


        // If we have only no matches we directly respond with groups list
        if (groups.size == 0) return groups
        if (requiresFiltering && groups[0] is UnrelatedMatchedGroup) return emptyList()
        // Final validation might be expensive, so we not always perform filtering
        if (validator.requiresFinalValidation()) {
            if (groups.size == 1) {
                if (validator.finalValidation(groups[0], board, groups)) {
                    return groups
                }
                return emptyList()
            } else {
                val set = LinkedHashSet<MatchGroup<CellType>>()
                for (group in groups) {
                    if (!(requiresFiltering && group is UnrelatedMatchedGroup) && validator.finalValidation(group, board, groups)) {
                        set.add(group)
                    }
                }
                return ArrayList(set)
            }
        } else if (requiresFiltering) {
            val set = LinkedHashSet<MatchGroup<CellType>>()
            for (group in groups) {
                if (group !is UnrelatedMatchedGroup) {
                    set.add(group)
                }
            }
            return ArrayList(set)
        } else {
            // If we have only one match, we directly respond with groups list
            return if (groups.size == 1) groups
            // otherwise we convert it to LinkedHashSet and back to get rid of
            // duplicate groups
            else ArrayList(LinkedHashSet(groups))
        }
    }

    private inline fun addMatch(
        curGroup: GrowingMatchGroup<CellType>,
        index: Int,
        newType: CellType,
        positionedGroups: IntArray,
        mergeCandidates: IntArrayList,
        addition: (GrowingMatchGroup<CellType>, Int) -> Unit = GrowingMatchGroup<CellType>::add
    ) {
        curGroup.type = newType
        addition(curGroup, index)

        val existingGroup = positionedGroups[index]
        if (existingGroup >= 0) {
            mergeCandidates.addIfNotExists(existingGroup)
        }
    }

    private fun newGroup(
        boardIndex: Int,
        lineIndex: Int,
        line: Line,
        cell: C,
        positionedGroups: IntArray,
        mergeCandidates: IntArrayList,
        board: B
    ): GrowingMatchGroup<CellType>? {
        // We have new group, great!
        // Not every cell can be used to start a match
        if (!matcher.canStartMatch(cell.type)) return null

        val curGroup = GrowingMatchGroup(cell.type, 0)
        addMatch(curGroup, boardIndex, cell.type, positionedGroups, mergeCandidates)

        // Sometimes we have to look back for cases like wildcards
        //
        // For a minor performance gain, first cell is extracted
        // before loop actually starts
        if (lineIndex == 0) return curGroup
        var lineI = lineIndex - 1
        var i = line[lineI]
        var behindCell = board.cells[i]
        if (!matcher.requiresBacktracking(behindCell.type)) return curGroup
        while (true) {
            val match = matcher.tryMatch(cell.type, behindCell.type) ?: break
            addMatch(curGroup, i, match, positionedGroups, mergeCandidates, GrowingMatchGroup<CellType>::addToFront)
            if (--lineI < 0) break
            i = line[lineI]
            behindCell = board.cells[i]
        }
        return curGroup
    }

    /**
     * Closes the active group
     *
     * @return true, if technicals groups were created and needs to be
     * filtered out before returning results
     */
    private fun closeGroup(
        curGroup: MatchGroup<CellType>,
        groups: MutableList<MatchGroup<CellType>>,
        positionedGroups: IntArray,
        mergeCandidates: IntArrayList,
        board: B
    ): Boolean {
        if (!validator.initialValidation(curGroup, board)) return false

        val index = groups.size
        var writeIndex = index
        var unrelatedGroupCreated = false

        if (DEBUG) {
            println("\n\nFound match! Index: $index\nMerge targets: $mergeCandidates\n${curGroup.displayOnBoard(board)}")
        }

        curGroup.index = index
        if (mergeCandidates.size == 0 && !mergeNeighbours) {
            // If we have no merge candidates, just add this group to the groups list
            groups.add(curGroup)
        } else {
            // Otherwise, new merge group is created to swallow all compatible
            // conflicting groups
            val merged = MergedMatchGroup(curGroup.type, index)
            var unrelatedGroup: UnrelatedMatchedGroup<CellType>? = null
            merged.add(curGroup)
            mergeCandidates(groups, merged, mergeCandidates) {
                // Sometimes conflicts can not be merged with current state
                // this can only happen via wildcards, and handling is not
                // very pretty
                if (unrelatedGroup == null) {
                    unrelatedGroup = UnrelatedMatchedGroup(curGroup.type, index + 1).apply { add(index) }
                    writeIndex = index + 1
                    unrelatedGroupCreated = true
                }
                unrelatedGroup!!.add(it)
            }

            if (mergeNeighbours) {
                val neighbours = IntArrayList()
                for (cell in curGroup.cells) {
                    for (neighbour in board.cells[cell].neighbours) {
                        val neighbourGroup = positionedGroups[neighbour]
                        if (neighbourGroup >= 0) {
                            if (mergeCandidates.contains(neighbourGroup)) continue
                            neighbours.addIfNotExists(neighbourGroup)
                        }
                    }
                }

                mergeCandidates(groups, merged, neighbours) {}
            }

            if (merged.size() > 1) {
                if (DEBUG) {
                    println("Match after merging:\n${merged.displayOnBoard(board)}")
                }
                groups.add(merged)
            } else {
                groups.add(curGroup)
            }

            if (unrelatedGroup != null) groups.add(unrelatedGroup!!)
        }

        // All cells from the current groups are marked with group ID
        for (cell in curGroup.cells) {
            positionedGroups[cell] = writeIndex
        }

        return unrelatedGroupCreated
    }

    /**
     * Iterates trough merge candidates, merging them if possible, or executing
     * [invalidCollisionAction] if merge is not possible
     */
    private inline fun mergeCandidates(
        groups: MutableList<MatchGroup<CellType>>,
        merged: MergedMatchGroup<CellType>,
        mergeCandidates: IntArrayList,
        invalidCollisionAction: (Int) -> Unit
    ) {
        // This kind of weird loop is used to avoid concurrent modification
        // exception due to unrolling UnrelatedMatchGroup
        var candidateIndex = 0
        while (candidateIndex < mergeCandidates.size) {
            val candidate = mergeCandidates[candidateIndex++]

            val other = groups[candidate]
            if (other === merged) continue
            // If incoming group is already merged, we just replace its
            // index value with merged group and move on
            if (merged.has(other)) {
                groups[candidate] = merged
                continue
            }

            // Upon encountering an unrelated group, just add its content
            // to merge candidates and move on
            if (other is UnrelatedMatchedGroup) {
                mergeCandidates.add(other.getGroups())
                continue
            }

            val newType = matcher.tryMatch(merged.type, other.type)

            // If merge failed, we execute collision action and move on
            if (newType == null) {
                invalidCollisionAction(candidate)
                continue
            }

            // But in case of successful match, we adopt merged type, and
            // add candidate to the merge
            merged.type = newType
            if (other is MergedMatchGroup) {
                for (group in other.getGroups()) {
                    groups[group.index] = merged
                    merged.add(group)
                }
            } else {
                merged.add(other)
                groups[candidate] = merged
            }
        }
    }
}