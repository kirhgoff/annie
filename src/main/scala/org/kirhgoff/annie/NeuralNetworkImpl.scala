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
    println(s"Neuron inputs=$inputs weights=$weights " +
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

  override def calculate(inputs: List[Double]): Double = {
    assert(inputs.length == layers.reverse.head.head.inputSize())
    val result = layers.foldRight(inputs)(
      (layer, outputs) => layer.map(neuron => neuron.output(outputs))
    )
    assert(result.length == 1)
    result.head
  }

  override def print():String = {
    val sb = new StringBuilder
    layers.reverse.zipWithIndex.map {
      case (layer: List[Neuron], index: Int) => {
        sb.append(s"========= Layer $index ============\n")
        layer.map(neuron => sb.append(s"${neuron.print()}\n"))
      }
    }
    sb.toString()
  }
}

object NetworkFactory {
  val random = new Random()
  val sigmoid = new Sigmoid

  def makeRandom(inputs:Int, layers:List[Int]):Network = {
    val initial = layers.head
    val result = layers.tail.foldRight(List(neuronList(inputs, initial))) {
      (count: Int, neuronLists: List[List[Neuron]])
      => neuronList(neuronLists.head.length, count) :: neuronLists
    }
    new NeuralNetworkImpl(result)
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
