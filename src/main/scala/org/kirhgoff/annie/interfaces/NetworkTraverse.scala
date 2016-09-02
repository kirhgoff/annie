package org.kirhgoff.annie.interfaces

/**
  * Created by kirilllastovirya on 3/09/2016.
  */
trait NetworkTraverse {
  def visitNeuron(index: Int, neuron: Neuron):Unit

  def visitLayer(index: Int):Unit

  def finished():Unit

}
