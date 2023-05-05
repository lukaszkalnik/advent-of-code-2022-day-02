# Advent of code 2022 - day 2

Today's task is parsing a list of rock-paper-scissors games and calculating total player points. The points are awarded
per game and also depending on the shape which the player chose.

## What I learned

* Create `enum Shape` for selected shapes and calculate points based on the ordinal
* If you parse arbitrary `Char`s to `enum`s, create a map of each char to corresponding `enum` - it will be faster to
  look up
* Don't put a game into a `Pair` - you will forget afterwards which element was the player and which one was the
  opponent. Prefer `data class` over tuples, as their fields are
  named - `Game(val opponentShape: Shape, val playerShape: Shape)`
* `GameResult` should also be an `enum`. The number of points can be calculated based on the `ordinal` as well, similar
  to selected `Shape` (see above).
* It's convenient to add the function calculating the game result to the `Shape` enum. The reason is, you have easy
  access to `this` Shape for comparison, so you can write it `fun gameResult(other: Shape): GameResult`
* Determining if this shape wins with other shape can be defined (assuming RPS order) by
  checking `ordinal == other.ordinal + 1` plus one check for the special case on the "overflow" (rock (0) wins with
  scissors (2)).
* Total game points can be calculated in the `Game` class based on the player's shape points and the calculated game
  result points.
* There is `List.sumOf { }` as shorthand for `List.map { }.sum()`