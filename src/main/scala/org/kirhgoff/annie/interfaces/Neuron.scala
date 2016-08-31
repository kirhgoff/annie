package org.kirhgoff.annie.interfaces

/**
  * Created by kirilllastovirya on 31/08/2016.
  */
trait Neuron {
  def output(inputs:List[Double]) : Double
}
