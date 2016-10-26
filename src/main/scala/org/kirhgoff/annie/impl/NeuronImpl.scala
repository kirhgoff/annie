package org.kirhgoff.annie.impl

import org.kirhgoff.annie.interfaces.{Neuron, TransferFunction}

class NeuronImpl(
                  val bias: Double,
                  val weights:List[Double],
                  val sigmoid:TransferFunction)
  extends Neuron {

  var result:Double = Double.NaN

  def output(inputs:List[Double]) : Double = {
    val sum = (inputs, weights).zipped.map((input, weight) => input*weight).sum
    result = sigmoid.function(bias + sum)
    println(s"Calculating neuron inputs=$inputs weights=$weights " +
      s"parameter=${bias + sum} result=$result")
    result
  }

  override def toString: String = {
    s"Neuron [weights=$weights bias=$bias sigmoid=$sigmoid]"
  }
}

