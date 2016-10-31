package org.kirhgoff.annie.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask

case class Layers(inputs:Int, layers: Traversable[Int])

private case class FirstLayer(actorRef: ActorRef)
private case class Calculate(inputs: List[Double])
private case class Result(inputs: List[Double])
private case class NeuronResult(output:Double)

class NeuralNetworkActor extends Actor {
  var firstLayer:ActorRef = _

  def building : Receive = {
    case FirstLayer(actorRef) =>
      this.firstLayer = actorRef
      context become active
  }

  def active : Receive = {
    case x:Calculate =>
      println(s"Input is ${x.inputs}")
      firstLayer ! x

    case Result(output:List[Double]) =>
      println(s"Output is: $output")
  }

  override def receive = building
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

object AnnieBuilder {
  def build(system:ActorSystem, schema: Layers) = {
    val network:ActorRef = system.actorOf(Props(new NeuralNetworkActor()), name="network")
    //TODO

//    schema.layers.foldRight(network)((layer:Int, nextActor) => {
//      (1 to layer).map(index => network.
//    })
    network
  }
}

/**
  * Main app
  */
object AnnieAkka {
  val input = List(
    1d, 0d, 0d,
    0d, 0d, 0d,
    0d, 0d, 0d)

  println(s"Calculating network with inputs:$input")

  val system = ActorSystem("annie-akka-system")
  val network = AnnieBuilder.build(system, Layers(inputs=9, layers=List(9, 1)))
  val future = network ? Calculate (input)

  val result = Await.result(future, 1.seconds).asInstanceOf[List[Double]]
  println(s"Result of calculation is $result")
}



