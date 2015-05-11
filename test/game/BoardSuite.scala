package game

import game.models.X
import org.specs2.mutable.Specification

class BoardSuite extends Specification with Fixtures{
  "Board" should {
    "print nicely" in {
      val nice = "X |   |  \nO | X | O\n  |   | X"

      diagonalWinningBoard.print mustEqual nice
    }

    "not be full" in {
      diagonalWinningBoard.isFull mustEqual false
    }

    "be full" in {
      fullDrawBoard.isFull mustEqual true
    }

    "throw exception when making move out of board" in {
      initialMoveBoard.placeMove(X, 10) must throwA[IndexOutOfBoundsException]
    }

    "throw exception when making move on ocupied position" in {
      initialMoveBoard.placeMove(X, 1) must throwA[IllegalArgumentException]
    }

    "should give the correct available positions" in {
      diagonalWinningBoard.availablePositions mustEqual Seq(2, 3, 7, 8)
    }
  }
}