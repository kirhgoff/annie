package org.kirhgoff.annie

import org.kirhgoff.annie.interfaces.{NetworkTraverse, Neuron}

import scala.util.Random

class PrintVisitor extends NetworkTraverse {
  override def visitNeuron(index: Int, neuron: Neuron)= println(neuron.print())
  override def visitLayer(index: Int) = println(s"========== Layer $index ========")
  override def finished() = {}
}

object AnnieApp  {
  def main(args: Array[String]): Unit = {
    println ("Annie app is running")
    val network = NetworkFactory.makeRandom(2, List(3, 1))
    network.print(new PrintVisitor)
    val result = network.calculate(List.tabulate(2)(_ => Random.nextDouble()))
    println(s"Result is $result")
  }
}

