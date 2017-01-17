package routes

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directive0, Route}
import akka.http.scaladsl.server.Directives._
import spray.json.DefaultJsonProtocol
import service.guestintimation.send_guest_intimation
import spray.json.{JsObject, JsString}

import scala.concurrent.ExecutionContext.Implicits._

/**
 * Created by 866317 on 12/28/2016.
 */
case class guest_login_request(flat_no:Int,guest_name:String,guest_type:String,guest_mobile_no:Long)
case class status(vs:Boolean,ys:String)
trait guestentryprotocol extends SprayJsonSupport with DefaultJsonProtocol
{
  implicit val guestentryFormat = jsonFormat4(guest_login_request)
}

trait guestentry extends guestentryprotocol {
  val guestroute:Route = pathPrefix("guestentry"){
     post{
        entity(as[guest_login_request]){
           ls =>
           {
             println(ls)
             val ps = send_guest_intimation(ls.flat_no,ls.guest_name,ls.guest_type,ls.guest_mobile_no)
             onSuccess(ps){
               case Some(ps) => println(ps.ys)
                 complete(StatusCodes.OK,JsString(ps.ys))
                 //complete(StatusCodes.OK, JsObject(Map("status" -> JsString(ps.ys))))

               case None => complete("OK")
             }
//             println(ps.map(ls => ls))
           }
        }
     }
   }
  }