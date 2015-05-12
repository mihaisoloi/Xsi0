package game

import game.models.{GameState, X}
import org.specs2.mutable.Specification
import game.ai.MinMax

class MinMaxSuite extends Specification with Fixtures{

  "AI" should {
    "pick the last open position" in {
      val ai = MinMax(X)
      val endGameState = ai.turn(lastMoveGameState)

      endGameState.isGameOver mustEqual true
    }

    "pick the corners or center if board is empty" in {
      val ai = MinMax(X)
      val nextGameState = ai.turn(GameState(emptyBoard, X))

      nextGameState.isBoardEmpty mustEqual false
      nextGameState.board.space.zipWithIndex.filter(_._1.isDefined).map(_._2) must containAnyOf(Seq(0, 2, 4, 6, 8))
    }

    "pick winning move" in {
      val ai = MinMax(X)
      val nextGameState = ai.turn(XtoWinGameState)

      nextGameState.isGameOver mustEqual true
      nextGameState.getWinningSymbol mustEqual Some(X)
    }

    "pick blocking move" in {
      val ai = MinMax(X)
      val nextGameState = ai.turn(xToBlockPossibleGameState)

      nextGameState.board.isPositionOccupied(2) mustEqual true
    }

    /**
     * Player will play random positions, algorithm must beat random or at least tie
     */
    "win or tie vs a random game play as 2nd player" in {
      import game.models._
      cycle(MinMax(O), GameState(emptyBoard, X)).getWinningSymbol must beOneOf(Some(O), Option.empty[Symbol])
    }

    "win or tie vs a random game play as 1st player" in {
      cycle(MinMax(X), xToBlockPossibleGameState).getWinningSymbol must beOneOf(Some(X), Option.empty[Symbol])
    }
  }
}
