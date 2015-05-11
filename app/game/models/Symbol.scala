package game.models

sealed trait Symbol {
  def str: String

  def other: Symbol = this match {
    case X => O
    case O => X
  }
}

case object X extends Symbol{
  val str = "X"
}

case object O extends Symbol{
  val str = "O"
}
