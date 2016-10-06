package org.kirhgoff.annie.backpropagation

import org.kirhgoff.annie.interfaces.TransferFunction

/**
  * Created by kirilllastovirya on 4/10/2016.
  */
class WeightedEdge {
  val input: NeuronVertex = ???
  val output: NeuronVertex = ???

  var weight = ???
}

class NeuronVertex {
  val sigmoid: TransferFunction = ???
  val incoming: List[WeightedEdge] = ???
  val outcoming: List[WeightedEdge] = ???

  var bias: Double = ???
}

//TODO use Akka



