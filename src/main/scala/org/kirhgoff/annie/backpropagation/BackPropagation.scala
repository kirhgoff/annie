package org.kirhgoff.annie.backpropagation

import org.kirhgoff.annie.interfaces.{LearnBySample, Network}

/**
  * Created by kirilllastovirya on 4/10/2016.
  */
class BackPropagation(val network:Network) extends LearnBySample {

  def reverse(network: Network, output: List[Double], result: List[Double])
    : NetworkBPImpl =
  {
    network
  }

  override def learn(input: List[Double], output: List[Double]): Network = {
    val result = network.calculate(input)
    val reversed:NetworkBPImpl = reverse (network, output, result:)

  }

}
