package game.models

sealed trait Board {

  lazy val nrOfRows = math.sqrt(space.size).toInt
  lazy val noSymbol = Option.empty[Symbol]

  /**
   * The option space of positions
   */
  def space: Vector[Option[Symbol]]

  /**
   * in the case of a draw, we know when to stop.
   */
  def isFull: Boolean

  /**
   * we need to know if there is a winning symbol in the sample space,
   *
   * Note: we can further parametrize this function with another function which will specify the condition,
   * but for now we are looking for 3 in a row or 3 in a diagonal
   */
  def findWinningSymbol: Option[Symbol]

  /**
   * prints the board space in the required format i.e.
   *  X | O | O
   *  O | X | X
   *  X | X | O
   */
  def print: String =
    space.map(_.map(_.str).getOrElse(" ")).grouped(nrOfRows).map(_.mkString(" | ")).mkString("\n")

  /**
   * Places Symbol, i.e. X or O, at the desired position.
   * Positions are mapped in space like so:
   *
   * 1 | 2 | 3
   * 4 | 5 | 6
   * 7 | 8 | 9
   *
   */
  def placeMove(symbol: Symbol, position: Int): Board = {
    val index = position - 1

    require(index >= 0 && index <= space.size, s"You can only enter values between 1 and ${space.size}")
    require(space(index).isEmpty, s"The board position $position is already taken")

    Board.Occupied(space = space.updated(index, Some(symbol)))
  }

  def isPositionOccupied(position: Int): Boolean =
    space(position - 1).isDefined

  /**
   * Choosing the corners and center is a good strategy in Xsi0
   */
  def mostFavorableStartingPositions =
    space.indices.groupBy(_ % (nrOfRows - 1))(0).map(_ + 1)


  /**
   * Returns the number of available positions, for 3x3 we have values in Range(1,9)
   */
  def availablePositions: Vector[Int] =
    space.zipWithIndex.filter(_._1.isEmpty).map(_._2 + 1)

  private[Board] def calculateWinningIndicesInSpace = {
    val columnIndices = space.indices.groupBy(_ % nrOfRows).values
    val rowIndices = space.indices.groupBy(_ / nrOfRows).values
    val upperLeftToLowerRightDiagonalIndices = (0 to nrOfRows - 1).map(_ * (nrOfRows + 1)) :: Nil
    val upperRightToLowerLeftDiagonalIndices = (1 to nrOfRows).map(_ * (nrOfRows - 1)) :: Nil

    columnIndices ++ rowIndices ++ upperLeftToLowerRightDiagonalIndices ++ upperRightToLowerLeftDiagonalIndices
  }
}

case object Board {

  /**
   * The representation of an Empty board
   */
  case class Empty(size: Int) extends Board {
    require(size >= 3, "Board size can't be smaller than 3x3")
    def isFull = false
    def findWinningSymbol = noSymbol

    override def space =
      Vector.fill(size * size)(noSymbol)
  }

  /**
   * The representation of an Occupied board, i.e. a board with at least one occupied position
   */
  case class Occupied(space: Vector[Option[Symbol]]) extends Board {
    def isFull = !space.exists(_.isEmpty)

    def findWinningSymbol =
      calculateWinningIndicesInSpace.map { indices =>
        // to make a mapping of the current space symbols over the winning indices
        val winningRowSymbols = indices.map(space)

        // we are checking to see if there is a winning symbol in the list of winning indices by seeing that all the
        // values in the row/column/diagonal are the same symbol
        winningRowSymbols.reduce((previous, current) => if(previous.equals(current)) previous else noSymbol)
      }.find(_.isDefined).getOrElse(noSymbol)
  }
}