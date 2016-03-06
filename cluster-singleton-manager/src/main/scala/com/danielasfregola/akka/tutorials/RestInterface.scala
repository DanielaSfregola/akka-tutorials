package com.danielasfregola.akka.tutorials

import scala.concurrent.ExecutionContext

import akka.http.scaladsl.server.{Directives, Route}

trait RestInterface extends Directives {

  implicit def executionContext: ExecutionContext

  val routes: Route =
    path("ping") {
      get {
        complete("pong")
    }
  }

}
