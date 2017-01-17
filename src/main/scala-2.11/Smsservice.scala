/**
  * Created by crramya on 11/01/17.
  */

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

import scala.concurrent.Future


  object TestSMS {
    def main(args:Array[String]): Unit ={
      implicit  val system = ActorSystem()
      implicit  val materlaizer = ActorMaterializer()
      val responseFuture:Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://smsgateway.me/api/v3/messages/send"))
      system.shutdown()

    }
   }

