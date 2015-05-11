package game.ai

import game.models.GameState

class Hotseat {

  /**
   * Each person takes turns until the game is over
   */
  def turn(state: GameState, position: Int) = {
    require(!state.isGameOver, "Game is already over!")

    state.placeMove(position)
  }
}
