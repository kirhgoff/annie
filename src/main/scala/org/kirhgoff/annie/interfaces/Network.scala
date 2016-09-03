package org.kirhgoff.annie.interfaces

/**
  * @author <a href="mailto:kirill.lastovirya@moex.com">Kirill Lastovirya</a>
  */
trait Network {
  def calculate(inputs:List[Double]):List[Double]
  def print(visitor: NetworkTraverse):Unit
}
