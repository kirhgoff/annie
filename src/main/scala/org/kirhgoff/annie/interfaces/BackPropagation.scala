package org.kirhgoff.annie.interfaces

/**
  * Created by kirilllastovirya on 3/09/2016.
  */
trait BackPropagation {
  def learn(input: List[Double], output: List[Double]):Network
}
