package com.danielasfregola.akka.tutorials.actors

import akka.actor.Actor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class TickCounter extends Actor {

  case class Tick(n: Int)

  override def preStart = self ! Tick(0)

  def receive = {
    case Tick(t) =>
      println(s"${self.path} - Tick $t")
      context.system.scheduler.scheduleOnce(1 second, self, Tick(t+1))
  }

}

