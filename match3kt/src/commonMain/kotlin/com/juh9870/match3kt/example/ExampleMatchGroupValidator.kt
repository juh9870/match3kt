package com.juh9870.match3kt.example

import com.juh9870.match3kt.core.matching.MatchGroup
import com.juh9870.match3kt.core.matching.MatchGroupValidator

class ExampleMatchGroupValidator(private val minSize: Int = 3) : MatchGroupValidator<ExampleBoard, ExampleCell, ExampleCellType> {
    override fun initialValidation(match: MatchGroup<ExampleCellType>, board: ExampleBoard): Boolean {
        return match.cells.size >= minSize
    }

    override fun finalValidation(match: MatchGroup<ExampleCellType>, board: ExampleBoard, groups: List<MatchGroup<ExampleCellType>>): Boolean {
        return true
    }

    override fun requiresFinalValidation(): Boolean {
        return false
    }
}