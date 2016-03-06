package com.danielasfregola.akka.tutorials.entities

case class QuestionUpdate(title: Option[String] = None, text: Option[String] = None)
