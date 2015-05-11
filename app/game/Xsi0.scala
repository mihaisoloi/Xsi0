package game

import scala.io.Source
import game.models._

/**
 * Xsi0 is the name of Tic-Tac-Toe in my mother-tongue it is literally translated to mean `X and 0`
 */
case object Xsi0{

  def main(args: Array[String]) = {
    println(
      s"""Welcome to Tic-Tac-Toe.
         |Please make your move selection by entering a number in range(1-9)
         |corresponding to the movement option on the right.""".stripMargin)

    println(
    s"""Please input the number of rows you want your Xsi0 to have(default: 3):"""
    )
    val readRows = readConsoleInput
    val rows = if(readRows.isEmpty) 3 else readRows
    println(
      s"""Please select your symbol X or O(default: X):"""
    )
    val readSymbol = readConsoleInput
    val symbol = if(readSymbol.isEmpty) X else readSymbol match {
      case "X" => X
      case "O" => O
      case _ => X
    }

    println(s"$rows~~~~~~~~~~~~$symbol")
  }

  def readConsoleInput: String =
    (for (ln <- Source.stdin.getLines())
      yield ln).toSeq.head
}