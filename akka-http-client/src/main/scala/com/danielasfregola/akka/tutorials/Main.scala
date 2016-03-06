package com.danielasfregola.akka.tutorials

import com.danielasfregola.akka.tutorials.clients.QuizManagementClient
import com.danielasfregola.akka.tutorials.entities.{Question, QuestionUpdate}

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {

  val client = new QuizManagementClient("localhost", 5000)

  val id = "test"
  val notExistingId = "not-existing-id"
  val q = Question(id = id, title = "MyTitle", text = "The text of my question")
  val qUpdate = QuestionUpdate(text = Some("Another text"))


  for {
    _ <- client.createQuestion(q).map { x => println(s"Created question '$x'")}
    _ <- client.getQuestion(id).map { x => println(s"Retrieved question with id '$id': $x") }
    _ <- client.getQuestion(notExistingId).map { x => println(s"Retrieved question with id '$notExistingId': $x") }
    _ <- client.updateQuestion(q.id, qUpdate).map { x => println(s"Updated question '${q.id}': $x") }
    _ <- client.deleteQuestion(id).map { _ => println(s"Question '$id' has been deleted") }
    _ <- client.deleteQuestion(notExistingId).map { _ => println(s"Question '$notExistingId' has been deleted") }
    _ <- client.getQuestion(id).map { x => println(s"Retrieved question with id '$id': $x") }
  } yield ()

}
