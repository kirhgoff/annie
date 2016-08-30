package org.kirhgoff.annie

/**
  * @author <a href="mailto:kirill.lastovirya@moex.com">Kirill Lastovirya</a>
  */
abstract class Neuron {
  val bias: Double
  val weights:List[Double]
  val sigmoid:TransferFunction

  def output(inputs:List[Double]) : Double = {
    val sum: Double = (inputs, weights).zipped.map((input, weight) => input*weight).sum
    sigmoid.function(bias + sum)
  }
}
