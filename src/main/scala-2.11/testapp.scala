import rais.rams.raiservices.CorsSupport
import routes.guestentry
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

/**
  * Created by crramya on 17/01/17.
  */
object testapp  extends  App with CorsSupport with guestentry{
  implicit  val system = ActorSystem()
  implicit  val materializer = ActorMaterializer()
  implicit  val executionContext = system.dispatcher
  Http().bindAndHandle(corsHandler(guestroute),"0.0.0.0",8780)
}

