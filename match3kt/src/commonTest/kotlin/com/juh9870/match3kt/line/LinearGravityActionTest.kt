package com.juh9870.match3kt.line
//
//import com.juh9870.match3kt.core.actions.gravity.GravityProperties
//import com.juh9870.match3kt.example.ExampleBoard
//import com.juh9870.match3kt.example.ExampleCell
//import com.juh9870.match3kt.example.ExampleCellType
//import com.juh9870.match3kt.utils.SquareGrid
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import kotlin.test.assertNotEquals
//
//internal class LinearGravityActionTest {
//
//    lateinit var gravity: LinearGravityAction<ExampleBoard, ExampleCell, ExampleCellType>
//    val properties: GravityProperties<ExampleBoard, ExampleCell, ExampleCellType> = GravityProperties.SimpleProperties()
//
////    @BeforeAll
////    fun beforeAll() {
//////        assert()
////    }
//
//    @Test
//    fun `Should not affect empty board`() {
//        val board = ExampleBoard.from("---", 1)
//        val gravity = LinearGravityAction(board, listOf(0), properties)
//
//        val affectedCells = LinkedHashSet<Int>()
//        gravity.run(board, affectedCells)
//
//        assertSame(ExampleBoard.from("---", 1), board)
//        assertEquals(0, affectedCells.size)
//    }
//
//    @Test
//    fun `Should fall along lines`() {
//        val board = ExampleBoard.from("RGB", "---", "---")
//        gravity = LinearGravityAction(SquareGrid.getVerticalLines(board, 3), properties)
//
//        val affectedCells = LinkedHashSet<Int>()
//        gravity.run(board, affectedCells)
//
//        assertSame(ExampleBoard.from("---", "---", "RGB"), board)
//        assertNotEquals(0, affectedCells.size)
//    }
//
//    @Test
//    fun `Should fall along weird lines`() {
//        val board = ExampleBoard.from("R-B", "---", "-G-")
//        gravity = LinearGravityAction(
//            arrayOf(
//                intArrayOf(0, 4, 8),
//                intArrayOf(7, 4, 1),
//                intArrayOf(2, 4, 6),
//            ), properties
//        )
//
//        val affectedCells = LinkedHashSet<Int>()
//        gravity.run(board, affectedCells)
//
//        assertSame(ExampleBoard.from("-G-", "---", "B-R"), board)
//        assertNotEquals(0, affectedCells.size)
//    }
//}
//
//fun assertSame(expected: ExampleBoard, actual: ExampleBoard) {
//    assertEquals(expected.toString(), actual.toString())
//}