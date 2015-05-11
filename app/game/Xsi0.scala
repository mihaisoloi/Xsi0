package game

import game.ai.MinMax

import scala.io.Source
import game.models._
import game.ai._

/**
 * Xsi0 is the name of Tic-Tac-Toe in my mother-tongue it is literally translated to mean `X and 0`
 */
case object Xsi0{

  def main(args: Array[String]) = {
    println(
      """Welcome to Tic-Tac-Toe.
         |Please make your move selection by entering a number in range(1-9)
         |corresponding to the movement option on the right.""".stripMargin)

    // Ask for number of rows

    print(
    """Please input the number of rows you want your Xsi0 to have(default: 3): """
    )
    val readRows = readConsoleInput
    val rows = if(readRows.isEmpty) 3 else readRows
    println(rows)

    // Ask for symbol

    print(
      """Please select your symbol X or O(default: X): """
    )
    val readSymbol = readConsoleInput
    val symbol = if(readSymbol.isEmpty) X else readSymbol match {
      case "X" => X
      case "O" => O
      case _ => X
    }
    println(symbol)

    // Choose between AI or Human

    print("""Please select vs AI or Human(default: AI): """)
    val readAI = readConsoleInput
    val ai = if(readAI.isEmpty) MinMax(symbol.other) else readAI.toLowerCase match {
      case "ai" => MinMax(symbol.other)
      case "human" => Hotseat
    }
    println(ai)

    // Let's start!!!



  }


//  @tailrec
//  final def cycle(ai: AI, gameState: GameState): GameState = {
//    if(gameState.isGameOver)
//      printAndReturnState(gameState)
//    else {
////      val playerMove = printAndReturnState(randomPlayerMove(gameState))
//      if(!playerMove.isGameOver)
//        cycle(ai, printAndReturnState(ai.turn(playerMove)))
//      else
//        cycle(ai, playerMove)
//    }
//  }

  def printAndReturnState(state: GameState): GameState = {
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println(state.board.print)
    state
  }

  def readConsoleInput: String =
    (for (ln <- Source.stdin.getLines())
      yield ln).toSeq.head
}