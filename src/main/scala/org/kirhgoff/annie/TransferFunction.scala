package org.kirhgoff.annie

/**
  * @author <a href="mailto:kirill.lastovirya@moex.com">Kirill Lastovirya</a>
  */
trait TransferFunction {
  def function(x:Double): Double
  def derivative(x:Double): Double
}

object Sigmoid extends TransferFunction {
  override def function(x : Double) : Double = 1 / (1 + Math.pow(Math.E, -1 * x))

  override def derivative(x: Double) : Double = {
    val result: Double = function(x)
    result * (1 - result)
  }
}
