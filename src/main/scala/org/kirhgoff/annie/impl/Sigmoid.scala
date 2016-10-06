package org.kirhgoff.annie.impl

import org.kirhgoff.annie.interfaces.TransferFunction

class Sigmoid extends TransferFunction {
  override def function(x : Double) : Double = 1 / (1 + Math.pow(Math.E, -1 * x))

  override def derivative(x: Double) : Double = {
    val result: Double = function(x)
    result * (1 - result)
  }

  override def toString = "1 / (1 + pow(e, -x))"
}
