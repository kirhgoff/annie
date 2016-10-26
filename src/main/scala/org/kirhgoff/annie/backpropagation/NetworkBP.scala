package org.kirhgoff.annie.backpropagation

import org.kirhgoff.annie.interfaces.{Neuron, TransferFunction}

/**
  * Created by kirilllastovirya on 4/10/2016.
  */
class WeightedEdge {
  val input: NeuronVertex = ???
  val output: NeuronVertex = ???

  var weight:Double = ???
}

class NeuronBP

class NeuronVertex extends NeuronBP {
  val sigmoid: TransferFunction = ???
  val incoming: List[WeightedEdge] = ???
  val outcoming: List[WeightedEdge] = ???

  var bias: Double = ???
}

class Input extends NeuronBP
class Output extends NeuronBP

class Processor {
  def registerInput(input:Input) = ???
  def registerOutput(output:Output) = ???
}

class NetworkBP {
}

//TODO use Akka



