package com.danielasfregola.akka.tutorials

import akka.actor.{PoisonPill, Props, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import akka.cluster.singleton.{ClusterSingletonManagerSettings, ClusterSingletonManager}
import com.danielasfregola.akka.tutorials.actors.TickCounter

import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

object Main extends App with RestInterface {
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  implicit val system = ActorSystem("akka-tutorials")
  implicit val materializer = ActorMaterializer()

  val tickCounter = {
    val singletonProps = ClusterSingletonManager.props(
      singletonProps = Props[TickCounter],
      terminationMessage = PoisonPill,
      settings = ClusterSingletonManagerSettings(system)
    )
    system.actorOf(singletonProps, "tick-counter-singleton")
  }

  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  val api = routes

  Http().bindAndHandle(handler = api, interface = host, port = port) map { binding =>
    println(s"REST interface bound to $host:$port") } recover { case ex =>
    println(s"REST interface could not bind to $host:$port", ex.getMessage)
  }

}
