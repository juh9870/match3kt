package com.juh9870.match3kt.line
//
//import com.juh9870.match3kt.core.SRandom
//import com.juh9870.match3kt.core.painter.ArrayCellDataProvider
//import com.juh9870.match3kt.core.painter.BoardPainter
//import com.juh9870.match3kt.example.*
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import kotlin.test.assertEquals
//
//internal class LineMatcherTest {
//    private lateinit var matcher: LineMatcher<ExampleBoard, ExampleCell, ExampleCellType>
//    private lateinit var painter: BoardPainter<ExampleCell, ExampleCellType>
//
//    @BeforeEach
//    fun beforeEach() {
//        matcher = LineMatcher(ExampleCellMatcher(), ExampleMatchGroupValidator(), true)
//        painter = BoardPainter(ArrayCellDataProvider(SRandom(0), arrayOf(ExampleCellType.RED, ExampleCellType.GREEN, ExampleCellType.BLUE)))
//    }
//
//    @Test
//    fun `Should not match below threeshold - horizontal`() {
//        val board = ExampleBoard.from("RR-")
//        val matches = matcher.findMatches(board)
//        assertEquals(0, matches.size)
//    }
//
//    @Test
//    fun `Should not match below threeshold - vertical`() {
//        val board = ExampleBoard.from("RR-", 1)
//        val matches = matcher.findMatches(board)
//        assertEquals(0, matches.size)
//    }
//
//    @Test
//    fun `Should not match different types`() {
//        val board = ExampleBoard.from("RRGG")
//        val matches = matcher.findMatches(board)
//        assertEquals(0, matches.size)
//    }
//
//    @Test
//    fun `Should not match empty board`() {
//        val board = ExampleBoard(5, 5)
//        painter.run {
//            pen(ExampleCellType.EMPTY)
//            board.fillBoard()
//        }
//        val matches = matcher.findMatches(board)
//        assertEquals(0, matches.size)
//    }
//
//    @Test
//    fun `Should match valid horizontal while not matching invalid vertical`() {
//        val board = ExampleBoard.from(
//            "RRR",
//            "R--",
//            "---",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(1, matches.size)
//        assertEquals(3, matches.first().cells.size)
//        assertEquals(ExampleCellType.RED, matches.first().type)
//    }
//
//    @Test
//    fun `Should match valid vertical while not matching invalid horizontal`() {
//        val board = ExampleBoard.from(
//            "RR-",
//            "R--",
//            "R--",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(1, matches.size)
//        assertEquals(3, matches.first().cells.size)
//        assertEquals(ExampleCellType.RED, matches.first().type)
//    }
//
//    @Test
//    fun `Should match valid - horizontal`() {
//        val board = ExampleBoard.from(
//            "RRR",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(1, matches.size)
//        assertEquals(3, matches.first().cells.size)
//        assertEquals(ExampleCellType.RED, matches.first().type)
//    }
//
//    @Test
//    fun `Should match valid - vertical`() {
//        val board = ExampleBoard.from(
//            "RRR", 1
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(1, matches.size)
//        assertEquals(3, matches.first().cells.size)
//        assertEquals(ExampleCellType.RED, matches.first().type)
//    }
//
//    @Test
//    fun `Should match separate groups`() {
//        val board = ExampleBoard.from(
//            "YRRRW",
//            "YGGGW",
//            "YBBBW",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(5, matches.size)
//        val uniqueColors = matches.map { it.type }.toSet()
//        assertEquals(5, uniqueColors.size)
//    }
//
//    @Test
//    fun `Should combine intersecting groups`() {
//        val board = ExampleBoard.from(
//            "-R-R-",
//            "RRRRR",
//            "-R-R-",
//            "RRRRR",
//            "-R-R-",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(1, matches.size)
//        assertEquals(16, matches.first().cells.size)
//        assertEquals(ExampleCellType.RED, matches.first().type)
//    }
//
//    @Test
//    fun `Should combine intersecting groups when mergeNeighbours=false`() {
//        matcher = LineMatcher(ExampleCellMatcher(), ExampleMatchGroupValidator(), false)
//        val board = ExampleBoard.from(
//            "-R-R-",
//            "RRRRR",
//            "-R-R-",
//            "RRRRR",
//            "-R-R-",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(1, matches.size)
//        assertEquals(16, matches.first().cells.size)
//        assertEquals(ExampleCellType.RED, matches.first().type)
//    }
//
//    @Test
//    fun `Should combine neighboring groups when mergeNeighbours=true`() {
//        val board = ExampleBoard.from(
//            "RRR--",
//            "--RRR",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(1, matches.size)
//        assertEquals(6, matches.first().cells.size)
//        assertEquals(ExampleCellType.RED, matches.first().type)
//    }
//
//    @Test
//    fun `Should not combine neighboring groups when mergeNeighbours=false`() {
//        matcher = LineMatcher(ExampleCellMatcher(), ExampleMatchGroupValidator(), false)
//        val board = ExampleBoard.from(
//            "RRR--",
//            "--RRR",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(2, matches.size)
//        assertEquals(3, matches[0].cells.size)
//        assertEquals(3, matches[1].cells.size)
//        assertEquals(ExampleCellType.RED, matches[0].type)
//        assertEquals(ExampleCellType.RED, matches[1].type)
//    }
//
//    @Test
//    fun `Should match on arbitrary boards`() {
//        // 0 1 2
//        // 3 4 5
//        // 6 7 8
//        val board = ExampleBoard(3, 3, lines = arrayOf(intArrayOf(0, 1, 2, 5, 8, 7, 6, 3, 4)))
//        painter.run {
//            pen(ExampleCellType.RED)
//            board.line(0, 0, 3)
//            pen(ExampleCellType.GREEN)
//            board.line(0, 3, 6)
//            pen(ExampleCellType.BLUE)
//            board.line(0, 6, 9)
//        }
//        println(board)
//        val matches = matcher.findMatches(board)
//        assertEquals(3, matches.size)
//        assertEquals(ExampleCellType.RED, matches[0].type)
//        assertEquals(ExampleCellType.GREEN, matches[1].type)
//        assertEquals(ExampleCellType.BLUE, matches[2].type)
//    }
//
//    @Test
//    fun `Should match wildcard - horizontal`() {
//        val board = ExampleBoard.from(
//            "R**",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(1, matches.size)
//        assertEquals(3, matches.first().cells.size)
//        assertEquals(ExampleCellType.RED, matches.first().type)
//    }
//
//    @Test
//    fun `Should match wildcard - vertical`() {
//        val board = ExampleBoard.from(
//            "R**", 1,
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(1, matches.size)
//        assertEquals(3, matches.first().cells.size)
//        assertEquals(ExampleCellType.RED, matches.first().type)
//    }
//
//    @Test
//    fun `Should not merge wildcard groups of different types`() {
//        val board = ExampleBoard.from(
//            "R**G", 1,
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(2, matches.size)
//        assertEquals(3, matches[0].cells.size)
//        assertEquals(3, matches[1].cells.size)
//        assertEquals(ExampleCellType.RED, matches[0].type)
//        assertEquals(ExampleCellType.GREEN, matches[1].type)
//    }
//
//    @Test
//    fun `Should merge wildcard groups`() {
//        val board = ExampleBoard.from(
//            "R**",
//            "--*",
//            "--R",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(1, matches.size)
//        assertEquals(5, matches[0].cells.size)
//        assertEquals(ExampleCellType.RED, matches[0].type)
//    }
//
//    @Test
//    fun `Should merge wildcard groups while avoiding different groups`() {
//        val board = ExampleBoard.from(
//            "--R",
//            "--*",
//            "R**",
//            "--*",
//            "--G",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(2, matches.size)
//        assertEquals(6, matches[0].cells.size)
//        assertEquals(4, matches[1].cells.size)
//        assertEquals(ExampleCellType.RED, matches[0].type)
//        assertEquals(ExampleCellType.GREEN, matches[1].type)
//    }
//
//    @Test
//    fun `Should not match wildcards only`() {
//        val board = ExampleBoard.from(
//            "***",
//        )
//        val matches = matcher.findMatches(board)
//        assertEquals(0, matches.size)
//    }
//}