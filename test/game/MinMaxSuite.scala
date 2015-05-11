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
      nextGameState.getWinningSymbol mustEqual X
    }
  }
}
