package org.kirhgoff.annie

import org.kirhgoff.annie.interfaces.{Network, Neuron, TransferFunction}

import scala.util.Random

/**
  * Created by kirilllastovirya on 31/08/2016.
  */

class Sigmoid extends TransferFunction {
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
    val sum = (inputs, weights).zipped.map((input, weight) => input*weight).sum
    val result = sigmoid.function(bias + sum)
    println(s"Neuron $name inputs=$inputs result=$result")
    result
  }
}

class NeuralNetworkImpl(
   val layers:List[List[Neuron]])
  extends Network {

  override def calculate(inputs: List[Double]): Double = {
    val result = layers.foldRight(inputs)(
      (layer, outputs) => layer.map(neuron => neuron.output(outputs))
    )
    assert(result.length == 1)
    result.head
  }
}

object NetworkFactory {
  val random = new Random()
  val sigmoid = new Sigmoid

  def apply(inputs:Int, layers:List[Int]):Network = {
    val size: Int = layers.head
    layers.tail.foldRight(List(neuronList(inputs, size)))
    ((count, neuronLists)=> )
    null
    //layers.foldRight(List.tabulate())
  }

  def neuronList(inputs: Int, count: Int): List[NeuronImpl] = {
    List.tabulate(count)(_ => newNeuron(inputs))
  }

  def newNeuron(inputs: Int): NeuronImpl = {
    new NeuronImpl("", randomDouble(), randomList(inputs), sigmoid)
  }

  def randomList(length:Int):List[Double] = List.tabulate(length)(_ => random.nextDouble())
  def randomDouble() = random.nextDouble()
}
