import okio.FileSystem
import okio.Path.Companion.toPath

fun main() {
    val path = "input.txt".toPath()
    val inputString = FileSystem.SYSTEM.read(path) { readUtf8() }.dropLast(1)
    val gameStrings = inputString.split("\n").map { line -> line.split(" ") }
    val games = gameStrings.map { game ->
        val opponentChar = game[0].single()
        val playerChar = game[1].single()
        Game(opponentShapes.getValue(opponentChar), playerShapes.getValue(playerChar))
    }
    val totalPoints = games.map { it.points }.sum()
    println(totalPoints)
}

enum class Shape {
    ROCK,
    PAPER,
    SCISSORS;

    val points: Int = ordinal + 1

    private fun winsWith(other: Shape): Boolean = ordinal == other.ordinal + 1 || (ordinal == 0 && other.ordinal == 2)

    /**
     * Call on a player [Shape] to determine player's [GameResult].
     */
    fun gameResult(opponentShape: Shape): GameResult =
        when {
            this == opponentShape -> GameResult.DRAW
            winsWith(opponentShape) -> GameResult.PLAYER_WON
            else -> GameResult.PLAYER_LOST
        }
}

val opponentShapes = mapOf(
    'A' to Shape.ROCK,
    'B' to Shape.PAPER,
    'C' to Shape.SCISSORS,
)

val playerShapes = mapOf(
    'X' to Shape.ROCK,
    'Y' to Shape.PAPER,
    'Z' to Shape.SCISSORS,
)

data class Game(val opponentShape: Shape, val playerShape: Shape) {

    val points: Int = playerShape.points + playerShape.gameResult(opponentShape).points
}

enum class GameResult {
    PLAYER_LOST,
    DRAW,
    PLAYER_WON;

    val points: Int = ordinal * 3
}