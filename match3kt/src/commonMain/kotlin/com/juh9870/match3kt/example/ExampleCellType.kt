package com.juh9870.match3kt.example

enum class ExampleCellType(private val stringValue: String) {
    EMPTY("-"),
    RED("R"),
    GREEN("G"),
    BLUE("B"),
    YELLOW("Y"),
    WHITE("W"),
    WILDCARD("*"),
    ;

    override fun toString(): String {
        return stringValue
    }

    companion object {
        fun fromChar(data: Char): ExampleCellType {
            return when (data) {
                'R' -> RED
                'G' -> GREEN
                'B' -> BLUE
                'Y' -> YELLOW
                'W' -> WHITE
                '*' -> WILDCARD
                else -> EMPTY
            }
        }
    }
}