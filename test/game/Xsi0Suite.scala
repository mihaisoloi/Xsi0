package game

import org.specs2.mutable.Specification

class Xsi0Suite extends Specification with Fixtures{

  "Game" should {
    "have empty board when starting" in {
      val game = Xsi0.apply

      game.board.isFull mustEqual false
    }
  }
}
