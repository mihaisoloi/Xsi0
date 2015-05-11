package game

import game.ai.AI
import game.models.Board.{Empty, Occupied}
import game.models.{GameState, O, X}

import scala.annotation.tailrec

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

  /**
   * O wins either way, i.e. if we block at 2 or at 9 it's the same thing
   */
  val impossibleBlockingPosition = Occupied(space = Vector(
    Some(O), None, Some(O),
    Some(X), Some(X), Some(O),
    Some(X), None, None
  ))
  
  val possibleBlockingPosition = Occupied(space = Vector(
    Some(X), Some(O), Some(X),
    None, Some(O), None,
    None, None, None
  ))

  val xToBlockImpossibleGameState = GameState(impossibleBlockingPosition, toMove = X)
  val xToBlockPossibleGameState = GameState(possibleBlockingPosition, toMove = X)

  val lastMoveGameState = GameState(lastOpenPosition, toMove = X)
  val XtoWinGameState = GameState(xtoWinBoard, toMove = X)

  def randomInAvailablePositions(availablePos: Vector[Int]) =
    availablePos((math.random * (availablePos.size - 1)).toInt)

  def randomPlayerMove(previousState: GameState): GameState =
    previousState.placeMove(randomInAvailablePositions(previousState.board.availablePositions))

  def printAndReturnState(state: GameState): GameState = {
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println(state.board.print)
    state
  }

  @tailrec
  final def cycle(ai: AI, gameState: GameState): GameState = {
    if(gameState.isGameOver)
      printAndReturnState(gameState)
    else {
      val playerMove = printAndReturnState(randomPlayerMove(gameState))
      if(!playerMove.isGameOver)
        cycle(ai, printAndReturnState(ai.turn(playerMove)))
      else
        cycle(ai, playerMove)
    }
  }
}