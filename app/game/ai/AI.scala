package game.ai

import game.models.{GameState, Symbol}

sealed trait AI {
  def turn(state: GameState): GameState
}

/**
 * Using the MinMax algorithm to make an invincible player, evil laughter
 */
case class MinMax(aiSymbol: Symbol) extends AI{

  val initialDepth = 0
  case class Node(score: Int, position: Int)

  def turn(state: GameState): GameState = {
    require(!state.isGameOver, "Game is already over!")

    state.placeMove(choosePosition(state))
  }

  def choosePosition(state: GameState): Int = {
    if(state.isBoardEmpty) {
      val startingPos = state.board.mostFavorableStartingPositions
      startingPos((math.random * (startingPos.size - 1)).toInt)
    } else if (state.board.availablePositions.size == 1)
      state.board.availablePositions.head
    else
      bestPossiblePosition(state)
  }

  def bestPossiblePosition(state: GameState): Int = {
    val score = state.board.availablePositions.size + 1
    minmax(state, initialDepth, score).position
  }

  def minmax(state: GameState, currentDepth: Int, baseScore: Int): Node = {
    // in the case of a draw, we'll still choose the best move, such that the other player doesn't win
    if(state.isGameOver)
      Node(scoreState(state, currentDepth, baseScore), Int.MinValue) // only care about the last move score, not the position
    else {
      val candidateNodes: Seq[Node] =
        for {
          position <- state.board.availablePositions

          childGameState = state.placeMove(position)
          nextNode = minmax(childGameState, currentDepth + 1, baseScore)
        } yield
          Node(nextNode.score, position)

      //do the min-max calculation
      if(state.toMove == aiSymbol)
        candidateNodes.maxBy(_.score)
      else
        candidateNodes.minBy(_.score)
    }
  }

  /**
   * Returns the score of the state based on the depth
   */
  def scoreState(state: GameState, depth: Int, baseScore: Int) =
    if(state.getWinningSymbol.exists(aiSymbol.equals)) // AI
      baseScore - depth
    else if(state.getWinningSymbol.exists(aiSymbol.other.equals)) // human
      depth - baseScore
    else
      0
}

case object Hotseat extends AI {

  /**
   * Each person takes turns until the game is over
   */
  def turn(state: GameState): GameState = {
    require(!state.isGameOver, "Game is already over!")

    state
  }
}