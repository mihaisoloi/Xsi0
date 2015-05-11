package game

import game.models.Board.{Empty, Occupied}
import game.models.{GameState, O, X}

trait Fixtures {

  val emptyBoard = Empty(size = 3)

  val initialMoveBoard = Occupied(space = Vector(
    Some(X), None, None,
    None, None, None,
    None, None, None
  ))

  val diagonalWinningBoard = Occupied(space = Vector(
    Some(X), None, None,
    Some(O), Some(X), Some(O),
    None, None, Some(X)
  ))

  val fullDrawBoard = Occupied(space = Vector(
    Some(X), Some(O), Some(X),
    Some(X), Some(O), Some(X),
    Some(O), Some(X), Some(O)
  ))

  val lastOpenPosition = Occupied(space = Vector(
    Some(X), Some(O), Some(X),
    Some(X), Some(O), Some(X),
    Some(O), None, Some(O)
  ))

  val xtoWinBoard = Occupied(space = Vector(
    Some(X), None, None,
    Some(X), None, Some(O),
    None, None, Some(O)
  ))

  val lastMoveGameState = GameState(lastOpenPosition, toMove = X)
  val XtoWinGameState = GameState(xtoWinBoard, toMove = X)
}
