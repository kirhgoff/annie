package org.kirhgoff.annie

import org.kirhgoff.annie.interfaces._

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

  override def toString = "1 / (1 + pow(e, -x))"
}

class NeuronImpl(
    val bias: Double,
    val weights:List[Double],
    val sigmoid:TransferFunction)
  extends Neuron {

  def output(inputs:List[Double]) : Double = {
    val sum = (inputs, weights).zipped.map((input, weight) => input*weight).sum
    val parameter: Double = bias + sum
    val result = sigmoid.function(parameter)
    println(s"Calculating neuron inputs=$inputs weights=$weights " +
      s"parameter=$parameter result=$result")
    result
  }

  override def inputSize(): Int = weights.length

  override def print(): String = {
    s"Neuron [${inputSize()} weights=$weights bias=$bias sigmoid=$sigmoid]"
  }
}

class NeuralNetworkImpl(
   val layers:List[List[Neuron]])
  extends Network {

  override def calculate(inputs: List[Double]): List[Double] = {
    println("Calculating...")
    assert(layers.head.forall(_.inputSize() == inputs.length))
    layers.foldLeft(inputs)(
      (outputs, layer) => layer.map(neuron => neuron.output(outputs))
    )
  }

  override def print(visitor:NetworkTraverse):Unit = {
    layers.zipWithIndex.foreach {
      case (layer: List[Neuron], index: Int) =>
        visitor.visitLayer(index)
        layer.foreach(neuron => visitor.visitNeuron(index, neuron))
    }
    visitor.finished()
  }
}

object NetworkFactory {
  val random = new Random()
  val sigmoid = new Sigmoid

  def makeRandom(inputs:Int, layerSizes:List[Int]):Network = {
    val initial = layerSizes.head
    val result = layerSizes.tail.foldLeft(List(neuronList(inputs, initial)))(
      (neuronLists: List[List[Neuron]], count: Int) => {
        neuronList(neuronLists.head.length, count) :: neuronLists
      })

    new NeuralNetworkImpl(result.reverse)
  }

  def neuronList(inputs: Int, count: Int): List[Neuron] = {
    List.tabulate(count)(_ => newNeuron(inputs))
  }

  def newNeuron(inputs: Int): Neuron = {
    new NeuronImpl(randomDouble(), randomList(inputs), sigmoid)
  }

  def randomList(length:Int):List[Double] =
    List.tabulate(length)(_ => random.nextDouble())

  def randomDouble() = random.nextDouble()

  def print(network:Network) = {
    println(network.toString)
  }
}

class BackPropagationImpl extends BackPropagation {
  override def learn(input: List[Double], output: List[Double]): Network = {
    
  }
}
