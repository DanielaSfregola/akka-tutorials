package com.danielasfregola.akka.tutorials.clients

import com.danielasfregola.akka.tutorials.entities.{QuestionUpdate, Question}
import akka.http.scaladsl.client.RequestBuilding._

import scala.concurrent.Future

class QuizManagementClient(host: String, port: Int) extends Client(host, port) {

  def createQuestion(question: Question): Future[String] =
    sendAndReceiveFromLocationHeader(Post(s"/questions", question))

  def getQuestion(id: String): Future[Option[Question]] =
    sendAndReceiveAs[Option[Question]](Get(s"/questions/$id"))

  def updateQuestion(id: String, update: QuestionUpdate): Future[Question] =
    sendAndReceiveAs[Question](Put(s"/questions/$id", update))

  def deleteQuestion(id: String): Future[Unit] =
    sendAndReceiveAsUnit(Delete(s"/questions/$id")).map(_ => ())

}
