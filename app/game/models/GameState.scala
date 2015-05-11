package game.models

/**
 * We need this state to know which of the symbols we must place next and to define the game over conditions
 */
case class GameState(board: Board, toMove: Symbol) {

  /**
   * This takes a value from 1 to 9
   */
  def placeMove(position: Int) =
    GameState(board.placeMove(toMove, position), nextSymbolToMove(toMove))

  def isBoardEmpty: Boolean =
    board.isInstanceOf[Board.Empty]

  def isGameOver: Boolean =
    board.isFull || board.findWinningSymbol.isDefined

  def getWinningSymbol: Option[Symbol] =
    board.findWinningSymbol

  def nextSymbolToMove(symbol: Symbol): Symbol = symbol match {
    case X => O
    case O => X
  }
}