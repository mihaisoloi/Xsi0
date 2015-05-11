package game.models

sealed trait Symbol {
  def str: String
}

case object X extends Symbol{
  val str = "X"
}

case object O extends Symbol{
  val str = "O"
}
