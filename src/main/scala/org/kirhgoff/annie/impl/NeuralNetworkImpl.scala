package org.kirhgoff.annie.impl

import org.kirhgoff.annie.interfaces._

/**
  * Created by kirilllastovirya on 31/08/2016.
  */

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


