package org.kirhgoff.annie.impl

import org.kirhgoff.annie.interfaces.{Network, Neuron}

import scala.util.Random

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
