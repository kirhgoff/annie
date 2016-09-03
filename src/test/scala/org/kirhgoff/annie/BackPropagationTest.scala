package org.kirhgoff.annie

import org.kirhgoff.annie.interfaces.BackPropagation
import org.scalatest._

class BackPropagationTest extends FlatSpec with Matchers {

  "NetwokFactory" should "create NNs" in {
    val network = NetworkFactory.makeRandom(1, List(1))
    val results: List[Double] = network.calculate(List(0.0))
    results.length should equal(1)
    results.head should be > 0.0
  }

  "NetwokFactory" should "should be able to learn" in {
    val network = NetworkFactory.makeRandom(1, List(2, 1))
    val firstResult = network.calculate(List(1d, 2d)).head

    val backprop = new BackPropagation(network)
    val newNetwork = backprop.learn(List(1d, 2d), List(0d))
    val secondResult = newNetwork.calculate(List(1d, 2d)).head

    (firstResult - secondResult) should be > 0d
  }
}
