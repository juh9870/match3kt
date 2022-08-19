import com.juh9870.match3kt.core.Cell
import com.juh9870.match3kt.core.SRandom
import com.juh9870.match3kt.core.actions.aftermatch.CombinedAfterMatchAction
import com.juh9870.match3kt.core.actions.aftermatch.EmptyCellsLookup
import com.juh9870.match3kt.core.board.Board
import com.juh9870.match3kt.core.matching.MatchGroup
import com.juh9870.match3kt.core.painter.ArrayCellDataProvider
import com.juh9870.match3kt.core.painter.BoardPainter
import com.juh9870.match3kt.example.*
import com.juh9870.match3kt.line.LineMatcher
import com.juh9870.serialization.deepCopy

fun main(args: Array<String>) {
    val seed = SRandom(9870)
    val board = ExampleBoard(6, 6)
//    val board = boardFrom(
//        "-RGBYW\n" +
//                "R*****\n" +
//                "G*****\n" +
//                "B*****\n" +
//                "Y*****\n" +
//                "******\n" +
//                "******\n" +
//                "W*****"
//    )
    val matcher = LineMatcher(ExampleCellMatcher(), ExampleMatchGroupValidator(3), true)
    val sink = ArrayCellDataProvider(
        SRandom(seed.nextLong()), arrayOf(
            ExampleCellType.RED,
            ExampleCellType.GREEN,
            ExampleCellType.BLUE,
//            SimpleCellType.WILDCARD,
        )
    )

    val filler = BoardPainter<ExampleCell, ExampleCellType>(sink)
    filler.run {
        board.run {
            penRandom()
//            pen(SimpleCellType.EMPTY)
            fillBoard()
//            pen(SimpleCellType.RED)
//            line(4, 1, 4)
//            line(1, 0, 4)
//            line(7, 0, 3)
        }
    }

    val postActions = CombinedAfterMatchAction.from<ExampleBoard, ExampleCell, ExampleCellType>(
        EmptyCellsLookup(),
//        { it, _, _ -> println("Before elimination:\n$it") },
//        EliminationAction(),
//        { it, _, _ -> println("After elimination:\n$it") },
//        GravityAction(board, (0 until board.width)),
//        { it, _, _ -> println("After gravity:\n$it") },
//        { it, _, empty -> filler.run { penRandom(); it.fillEmpty(); empty.clear() } },
    )

    run {
        var turns = 0
        do {
            println(board)
            val matches = matcher.findMatches(board)
            if (matches.isEmpty()) break
            postActions.processMatches(board, matches, HashSet(), LinkedHashSet())

            for (match in matches) {
                displayMatch(match, filler, board, ExampleCellType.EMPTY)
            }
        } while (turns-- > 0)
    }

    if (true) {
        benchmark(
            8, 8, ArrayCellDataProvider(
                SRandom(seed.nextLong()), arrayOf(
                    ExampleCellType.RED
                )
            ), matcher, "1-color", 15000
        )
        benchmark(
            8, 8, ArrayCellDataProvider(
                SRandom(seed.nextLong()), arrayOf(
                    ExampleCellType.RED,
                    ExampleCellType.GREEN
                )
            ), matcher, "2-color", 15000
        )
        benchmark(
            8, 8, ArrayCellDataProvider(
                SRandom(seed.nextLong()), arrayOf(
                    ExampleCellType.RED,
                    ExampleCellType.GREEN,
                    ExampleCellType.BLUE,
                )
            ), matcher, "3-color", 30000
        )
        benchmark(
            8, 8, ArrayCellDataProvider(
                SRandom(seed.nextLong()), arrayOf(
                    ExampleCellType.RED,
                    ExampleCellType.GREEN,
                    ExampleCellType.BLUE,
                    ExampleCellType.YELLOW,
                    ExampleCellType.WHITE,
                )
            ), matcher, "5-color", 30000
        )
    }
}

fun benchmark(w: Int, h: Int, provider: ArrayCellDataProvider<ExampleCellType>, matcher: LineMatcher<ExampleBoard, ExampleCell, ExampleCellType>, name: String, time: Int) {
    val perfBoard = ExampleBoard(w, h)
    var elapsed = 0L
    var ungroupingTime = 0L
    var count = 0
    val filler = BoardPainter(provider)
    var r = SRandom(9871)
    filler.penRandom()
    do {
        filler.run {
            perfBoard.fillBoard()
//            perfBoard.cells[r.nextInt(perfBoard.cells.size)].type = ExampleCellType.WILDCARD
        }
//        val start = System.currentTimeMillis()
        val matches = matcher.findMatches(perfBoard)
        count++
//        for (match in matches) {
//            val startNano = System.nanoTime();
//            match.cells
//            ungroupingTime += System.nanoTime() - startNano
//        }
//        elapsed += System.currentTimeMillis() - start
    } while (elapsed < time)
    println("Matched ${w}x$h $name board ${1f * count / elapsed * 1000} times in one second." /*+ "Computing cells took ${round(ungroupingTime / 1e6 / count * 10000) / 10000}ms per match"*/)
}

fun <C : Cell<T>, T> displayMatch(match: MatchGroup<T>, painter: BoardPainter<C, T>, board: Board<C, T>, empty: T) {
    val newBoard = board.deepCopy()
    newBoard.run {
        painter.run {
            pen(empty)
            fillBoard()
            pen(match.type)
            for (cell in match.cells) {
                set(cell)
            }
        }
    }
    println(newBoard)
}