package org.kirhgoff.annie

import com.typesafe.scalalogging.LazyLogging

import scala.util.Random

object AnnieApp  {
  def main(args: Array[String]): Unit = {
    println ("Annie app is running")
    val network = NetworkFactory.makeRandom(2, List(3, 1))
    println(network.print())
    val result = network.calculate(List.tabulate(2)(_ => Random.nextDouble()))
    println(s"Result is $result")
  }
}

