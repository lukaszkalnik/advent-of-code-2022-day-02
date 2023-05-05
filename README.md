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

# Part 2 plot twist

Turns out the developer shouldn't have guessed the requirements on his/her own, but ask the client... When the elf came
back, turned out the second character in respective input lines encoded the desired game result.
So to get the number of points now we have to find a player shape which enables us to achieve the given result.

## What I learned from part 2

* If your customer reverses input and output, maybe you can reverse your functions. Not quite, but based on
  the `Shape.gameResult(Shape)` and `winsWith(Shape)` functions I could write "reverse"
  functions: `playerShape(GameResult)` and `relatedOrdinal(winning: Boolean)`
* There was also a potential for optimization - instead of checking the special rock/scissors case (ordinals 0 and 2), I
  could in the `relatedOrdinal()` function just add a distance (1 for winning, 2 for losing) and then use modulo 3 to
  return the desired ordinal.