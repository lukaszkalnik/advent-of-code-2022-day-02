import okio.FileSystem
import okio.Path.Companion.toPath

fun main() {
    val path = "input.txt".toPath()
    val inputString = FileSystem.SYSTEM.read(path) { readUtf8() }.dropLast(1)
    val gameSetupStrings = inputString.split("\n").map { line -> line.split(" ") }
    val gameSetups = gameSetupStrings.map { game ->
        val opponentChar = game[0].single()
        val playerChar = game[1].single()
        Pair(opponentShapes.getValue(opponentChar), gameResults.getValue(playerChar))
    }
    val totalPoints = gameSetups.sumOf { (opponentShape, result) ->
        val shapePoints = opponentShape.playerShape(result).points
        shapePoints + result.points
    }
    println(totalPoints)
}

enum class Shape {
    ROCK,
    PAPER,
    SCISSORS;

    val points: Int = ordinal + 1

    /**
     * Returns ordinal of a [Shape], which is [winning] (or not) with this [Shape].
     */
    private fun relatedOrdinal(winning: Boolean): Int {
        val distance = if (winning) 1 else 2
        return (ordinal + distance) % 3
    }

    /**
     * Call on an opponent [Shape] to determine player shape to achieve [result].
     */
    fun playerShape(result: GameResult): Shape =
        when (result) {
            GameResult.DRAW -> this

            GameResult.PLAYER_WON -> {
                val winningOrdinal = relatedOrdinal(winning = true)
                Shape.values()[winningOrdinal]
            }

            GameResult.PLAYER_LOST -> {
                val losingOrdinal = relatedOrdinal(winning = false)
                Shape.values()[losingOrdinal]
            }
        }
}

val opponentShapes = mapOf(
    'A' to Shape.ROCK,
    'B' to Shape.PAPER,
    'C' to Shape.SCISSORS,
)

val gameResults = mapOf(
    'X' to GameResult.PLAYER_LOST,
    'Y' to GameResult.DRAW,
    'Z' to GameResult.PLAYER_WON,
)

enum class GameResult {
    PLAYER_LOST,
    DRAW,
    PLAYER_WON;

    val points: Int = ordinal * 3
}
