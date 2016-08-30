package org.kirhgoff.annie

import akka.actor.{ActorSystem, Props}
import com.typesafe.scalalogging.LazyLogging

object AnnieApp extends LazyLogging {
  def main(args: Array[String]): Unit = {
    logger.info ("Annie app is running")
  }
}

