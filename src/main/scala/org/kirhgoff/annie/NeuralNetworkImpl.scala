package org.kirhgoff.annie

import org.kirhgoff.annie.interfaces.{Network, Neuron, TransferFunction}

/**
  * Created by kirilllastovirya on 31/08/2016.
  */

object Sigmoid extends TransferFunction {
  override def function(x : Double) : Double = 1 / (1 + Math.pow(Math.E, -1 * x))

  override def derivative(x: Double) : Double = {
    val result: Double = function(x)
    result * (1 - result)
  }
}

class NeuronImpl(
    val name:String,
    val bias: Double,
    val weights:List[Double],
    val sigmoid:TransferFunction)
  extends Neuron {

  def output(inputs:List[Double]) : Double = {
    val sum: Double = (inputs, weights).zipped
      .map((input, weight) => input*weight).sum
    val result: Double = sigmoid.function(bias + sum)
    println(s"Neuron $name inputs=$inputs result=$result")
    result
  }
}

class NeuralNetworkImpl(
   val layers:List[List[Neuron]])
  extends Network {

  override def calculate(inputs: List[Double]): Double = {
    val result: List[Double] = layers.foldRight(inputs)(
      (layer, outputs) => layer.map(neuron => neuron.output(outputs))
    )
    assert(result.length == 1)
    result.head
  }
}

object NetworkFactory {
  def apply(inputs:Int, layers:List[Int]):Network = {
    //layers.foldRight(List.tabulate())
  }
}
