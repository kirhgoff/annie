package org.kirhgoff.annie

import com.typesafe.scalalogging.LazyLogging

object AnnieApp extends LazyLogging {
  def main(args: Array[String]): Unit = {
    logger.info ("Annie app is running")
    val network = NetworkFactory.makeRandom(2, List(2, 1))
    val result = network.calculate((0 to 8).map(_ => 0d).toList)
    println(s"Result is $result")
  }
}

