package org.kirhgoff.annie.akka

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask

case class Layers(inputs:Int, layers: List[Int])
private case class LayerSchema(inputs:Int, neurons:Int)

private case class BuildLayers(schema:Layers)
private case class BuildNeurons(inputs:Int, count:Int, next:ActorRef)

private case class Calculate(inputs: List[Double])
private case class Result(inputs: List[Double])
private case class NeuronResult(output:Double)



class NeuralNetworkActor extends Actor {
  var firstLayer:ActorRef = _

  def building : Receive = {
    case BuildLayers(schema) =>
      val layersCount: Int = schema.layers.length
      val layerActors:List[ActorRef] =
        List.fill(layersCount)(context.actorOf(Props(new LayerActor())))

      firstLayer = layerActors.head

      val inputs:List[Int] = schema.inputs :: schema.layers.dropRight(1)
      val nexts:List[ActorRef] = layerActors.tail :+ self //for the last one the next is me
      for (input <- inputs; count <- schema.layers; next <- nexts; actor <- layerActors )
        actor ! BuildNeurons(input, count, next)

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

class LayerActor() extends Actor {
  var nextLayer:ActorRef = _
  var neurons:List[ActorRef] = _

  val tempResults:mutable.MutableList[NeuronResult] = mutable.MutableList()

  def building: Receive = {
    case BuildNeurons(inputs, count, next) => {
      nextLayer = next
      neurons = List.fill(count)(context.actorOf(Props(new NeuronActor())))
    }
  }

  def active: Receive = {
    case x:Calculate => neurons.foreach(_ ! x)
    case x:NeuronResult =>
      tempResults += x
      if (tempResults.length == neurons.length) {
        val values: List[Double] = tempResults.toList.map(_.output)
        tempResults.clear()
        nextLayer ! Calculate(values)
      }
  }

  override def receive = building
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



