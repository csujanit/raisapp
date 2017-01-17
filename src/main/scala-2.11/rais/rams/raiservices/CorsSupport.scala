package rais.rams.raiservices

import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.{StatusCodes, HttpResponse}
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.{Route, Directive0, Directive}
import com.typesafe.config.{ConfigFactory}
import akka.http.scaladsl.server.Directives._
/**
 * Created by 866317 on 10/21/2016.
 */
trait CorsSupport {
  lazy val allowedOrigin = {
    val config = ConfigFactory.load()
    //val sAllowedOrigin = config.getString("cors.allowed-origin")
    //HttpOrigin(sAllowedOrigin)
  }

  private def addAccessControllers:Directive0={
    respondWithHeaders(
      `Access-Control-Allow-Origin`.*,
      //`Access-Control-Allow-Origin`(HttpOrigin("/")),
      //`Access-Control-Allow-Origin`.forRange(HttpOriginRange.*),
      `Access-Control-Allow-Credentials`(true),
      `Access-Control-Allow-Headers`("Origin","Authorization", "Content-Type", "X-Requested-With","access-control-allow-origin","Access-Control-Allow-Origin","Accept")

    )
  }

  private  def preflightRequestHandler:Route = options{
    complete(HttpResponse(StatusCodes.OK).withHeaders(`Access-Control-Allow-Methods`(OPTIONS,POST, PUT, GET, DELETE)))
    //,`Access-Control-Request-Method`(POST)
  }

  def corsHandler(r:Route) = addAccessControllers {
    preflightRequestHandler ~ r
  }
}
