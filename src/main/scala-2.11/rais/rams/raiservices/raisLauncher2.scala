package rais.rams.raiservices

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import routes.guestentry
import scala.io.StdIn
import akka.http.scaladsl.server.Directives._


/**
 * Created by 866317 on 10/21/2016.
 */
object raisLauncher2  extends  App with raisAppService  with CorsSupport with guestentry {
    implicit  val system = ActorSystem()
    implicit  val materializer = ActorMaterializer()
    implicit  val executionContext = system.dispatcher
//    val bindingFuture = Http().bindAndHandle(corsHandler(route ~ app_route ~ testRoute ~ guestroute ),"localhost",8780)
val bindingFuture = Http().bindAndHandle(corsHandler(route ~  guestroute ),"192.168.1.146",8780)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate())
}

