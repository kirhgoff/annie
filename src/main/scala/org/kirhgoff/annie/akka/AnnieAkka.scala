package org.kirhgoff.annie.akka

import akka.actor.{Actor, ActorRef}

import scala.collection.mutable

private case class Neuron(weights:List[Double], bias:Double, function:Double=>Double)
private case class Layer(neurons:List[Neuron])

case class Calculate(inputs: List[Double])
case class Result(inputs: List[Double])
case class NeuronResult(output:Double)

object NeuralNetworkBuilder {
  def build (layers:List[Layer]):NeuralNetworkActor = {
    
  }
}

class NeuralNetworkActor(val firstLayer:ActorRef) extends Actor {
  override def receive = {
    case x:Calculate =>
      println(s"Input is ${x.inputs}")
      firstLayer ! x

    case Result(output:List[Double]) =>
      println(s"Output is: $output")
  }
}

class OutputLayer(val receiver:ActorRef) extends Actor {
  override def receive = {
    case _ => receiver ! _
  }
}

class LayerActor(val neurons:List[ActorRef], val nextLayer:ActorRef) extends Actor {
  val tempResults:mutable.MutableList[NeuronResult] = mutable.MutableList()
  override def receive = {
    case x:Calculate => neurons.foreach(_ ! x)
    case x:NeuronResult =>
      tempResults += x
      if (tempResults.length == neurons.length) {
        val values: List[Double] = tempResults.toList.map(_.output)
        tempResults.clear()
        nextLayer ! Calculate(values)
      }
  }
}

class NeuronActor(val weights:List[Double],
                   val bias:Double,
                   val function:Double=>Double) extends Actor {

  override def receive = {
    case Calculate(inputs:List[Double]) =>
      val sum:Double = (inputs zip weights).map(t => t._1 * t._2).sum
      sender ! NeuronResult(bias + function.apply(sum))
  }

}


/**
  * Created by kirilllastovirya on 11/10/2016.
  */
object AnnieAkka {

}



