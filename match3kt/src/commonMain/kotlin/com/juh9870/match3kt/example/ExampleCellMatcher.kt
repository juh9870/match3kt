package com.juh9870.match3kt.example

import com.juh9870.match3kt.line.LinearCellMatcher

class ExampleCellMatcher : LinearCellMatcher<ExampleCellType> {
    override fun tryMatch(current: ExampleCellType, other: ExampleCellType): ExampleCellType? {
        if (current == other) return current
        if (current == ExampleCellType.WILDCARD) return other
        if (other == ExampleCellType.WILDCARD) return current
        return null
    }

    override fun canStartMatch(data: ExampleCellType): Boolean {
        return data != ExampleCellType.EMPTY && data != ExampleCellType.WILDCARD
    }

    override fun requiresBacktracking(data: ExampleCellType): Boolean {
        return data == ExampleCellType.WILDCARD
    }
}