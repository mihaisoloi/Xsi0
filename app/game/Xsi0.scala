package game

import game.models.Board

/**
 * Xsi0 is the name of Tic-Tac-Toe in my mother-tongue it is literally translated to mean `X and 0`
 */
case class Xsi0 private(board: Board) {


}

object Xsi0 {

  def apply: Xsi0 =
    Xsi0(Board.Empty(size = 3))

  def apply(size: Int): Xsi0 =
    Xsi0(Board.Empty(size = size))
}