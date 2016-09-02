package org.kirhgoff.annie.interfaces

/**
  * Created by kirilllastovirya on 31/08/2016.
  */
trait Neuron {
  def print(): String

  def inputSize():Int

  def output(inputs:List[Double]) : Double
}
