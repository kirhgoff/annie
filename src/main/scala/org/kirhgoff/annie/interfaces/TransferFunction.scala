package org.kirhgoff.annie.interfaces

/**
  * @author <a href="mailto:kirill.lastovirya@moex.com">Kirill Lastovirya</a>
  */
trait TransferFunction {
  def function(x:Double): Double
  def derivative(x:Double): Double
}
