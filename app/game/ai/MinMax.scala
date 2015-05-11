package game.ai

import game.models.{GameState, Symbol}

/**
 * Using the MinMax algorithm to make an invincible player, evil laughter
 */
case class MinMax(aiSymbol: Symbol) {

  val initialDepth = 0
  case class Node(score: Int, position: Int)

  def turn(state: GameState) = {
    require(!state.isGameOver, "Game is already over!")

    state.placeMove(choosePosition(state))
  }

  def choosePosition(state: GameState): Int = {
    if(state.isBoardEmpty)
      state.board.mostFavorableStartingPositions((math.random * state.board.space.size).toInt)
    else if (state.board.availablePositions.size == 1)
      state.board.availablePositions.head
    else
      bestPossiblePosition(state)
  }

  def bestPossiblePosition(state: GameState): Int = {
    val score = state.board.availablePositions.size + 1
    minmax(state, initialDepth, Node(score, state.board.availablePositions.head)).position
  }

  def minmax(state: GameState, currentDepth: Int, previousNode: Node): Node = {
    if(state.isGameOver)
      Node(score = evaluateState(state, currentDepth, previousNode.score), position = previousNode.position)
    else {
      val candidateNodes: Seq[Node] =
        for {
          position <- state.board.availablePositions.tail

          childGameState = state.placeMove(position)
        } yield
          minmax(childGameState, currentDepth + 1, previousNode)

      //do the min-max calculation
      if(state.toMove == aiSymbol)
        candidateNodes.maxBy(n => n.score)
      else
        candidateNodes.minBy(n => n.score)
    }
  }

  /**
   * Returns the score of the state based on the depth
   */
  def evaluateState(state: GameState, depth: Int, score: Int) =
    if(state.getWinningSymbol.exists(aiSymbol.equals)) // AI
      score - depth
    else if(state.getWinningSymbol.exists(state.nextSymbolToMove(aiSymbol).equals)) // human
      depth - score
    else
      0
}