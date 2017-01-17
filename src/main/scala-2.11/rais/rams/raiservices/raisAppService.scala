package rais.rams.raiservices


import akka.http.javadsl.server.PathMatchers
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json.DefaultJsonProtocol
import scala.concurrent.Future
import com.wix.accord.dsl._

/**
 * Created by 866317 on 10/21/2016.
 */
case class Customer(appt_id:String,mobile_number:BigInt,password:String,login_ind:String)
case class Item(name: String, id: Long)
case class LoginRequest(username:String,password:String)
case class LoginResponse(token:String)

trait LoginJsonFormatter extends SprayJsonSupport with DefaultJsonProtocol {
  implicit  val loginRequestValidation = validator[LoginRequest]{
        request =>
      request.password is notEmpty
      request.username is notEmpty
      request.password.length() as "password:length" should be > 5
  }
  implicit  val loginRequestFormat = jsonFormat2(LoginRequest)
//  implicit  val loginResponseFormat = jsonFormat2(LoginResponse)
}

trait raisAppServiceProtocol extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val CustomerFormat = jsonFormat4(Customer)
}

trait raisAppService  extends raisAppServiceProtocol {
  val getmethod = pathPrefix("raisapp" / IntNumber)
  val route = {
    getmethod {
      { vs =>
        (path(Segment) ) {
          ls:String =>{
            ls match  {
              case "verify"  => get{
                complete("OK")
              }
              case "apt_search" => get{
                complete("SUCCESS")
              }
            }
          }
        }
      }
    }
  }





    lazy val app_route = path("appt_search" / IntNumber)(
      id =>
        get {
          extractMethod {
            m =>
              complete(s"Recieved ${m.name}")
          }
        }
    )


    val appt_route = path("order" / IntNumber) & get | parameter('order.as[Int])


    val testRoute: Route = {
      appt_route { ls =>
        parameter("orderid".as[Int], "oem") {
          (orderid, oem) => println(orderid)
            complete("OK")
        }
      } ~ {
        appt_route {
          ls =>
            parameter("mobileno".as[String]) {
              (mobileno) => println(mobileno)
                complete(mobileno)
            }
        }
      }

    }

}



