package com.danielasfregola.akka.tutorials.clients

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{StatusCodes, HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import com.danielasfregola.akka.tutorials.serializers.JsonSupport

import scala.concurrent.{ExecutionContext, Future}

class Client(host: String, port: Int) extends JsonSupport {

  implicit val system = ActorSystem("akka-tutorials")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext: ExecutionContext = system.dispatcher

  val httpClient = Http().outgoingConnection(host = host, port = port)

  def sendAndReceiveFromLocationHeader(httpRequest: HttpRequest): Future[String] = {
    val unmarshalFromLocationHeader: HttpResponse => Future[String] = { response =>
      val id = response.headers.find(_.name == "Location").map( h => h.value.split("/").last)
      id.map(Future(_)).getOrElse(
        Future.failed(new Exception(s"No Location Header found. Response was: $response")))
    }
    sendAndReceive(httpRequest, unmarshalFromLocationHeader)
  }

  def sendAndReceiveAsUnit(httpRequest: HttpRequest): Future[Unit] =
    sendAndReceive(httpRequest, {response => Future(())})

  def sendAndReceiveAs[T: Manifest](httpRequest: HttpRequest): Future[T] =
    sendAndReceive(httpRequest, response => json4sUnmarshaller[T].apply(response.entity))

  private def sendAndReceive[T](request: HttpRequest, f: HttpResponse => Future[T]): Future[T] =
    Source.single(request)
    .via(httpClient)
    .mapAsync(1) { response =>
      if (response.status.isSuccess || response.status == StatusCodes.NotFound) f(response)
      else Future.failed(new Exception(s"Request failed. Response was: $response"))
    }
    .runWith(Sink.head)
}
