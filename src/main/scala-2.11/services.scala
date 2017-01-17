/**
 * Created by 866317 on 12/28/2016.
 */
package service



import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest}
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits._
import db.DB.execute
import domain.FlatUser.flatuserFromRowData
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.Http.OutgoingConnection
case class status(vs:Boolean,ys:String)


object guestintimation extends LazyLogging {
  implicit  val executionContext:ExecutionContext = global
  def send_guest_intimation(flat_no:Int,guest_name:String,guest_type:String,guest_mobile_no:Long):Future[Option[status]] ={
    logger.info(s"Guest Intimation Entry for flat $flat_no")
    execute("SELECT * from FLATS WHERE flat_no = ?",flat_no) map {
      data =>
        data.rows match
        {
        case Some(resultSet) =>
          resultSet.length match
          {
          case 0 => logger.info(s"Flat Resident with $flat_no doesnot exist")
            Some(status(false,"T3433"))
          case _ =>
                   println(resultSet.head.apply("mobile_no").toString.toLong)
                   val flatuser = flatuserFromRowData(resultSet.head)
                   flatuser.active_status match {
                     case "Y" =>
                       {
                         val guest_entry_time = DateTime.now.toDateTime.toString
                         val guest_insert_status =
                           execute("INSERT INTO GUEST_ENTRY_TABLE values(?,?,?,?,?)",flatuser.flat_no,guest_name,guest_mobile_no.toString,guest_type,guest_entry_time).map
                           {
                             vs =>
                               vs.rows match
                              {
                               case Some(resultSet) =>  sms_send_status(flatuser.first_name,flatuser.flat_no,guest_name,guest_type,guest_entry_time,guest_mobile_no );
                               case _ => false
                              }
                           }
                         Some(status(guest_insert_status.isCompleted,"T0001"))
                       }
                     case "N" =>
                       Some(status(false,"T0002"))
                     }

        }
        case _ => println("excpetion") ;Some(status(false,"T0003"))

      }

    }

  }

  @throws(classOf[java.io.IOException])
  @throws(classOf[java.net.SocketTimeoutException])
  def sms_send_status(first_name:String,flat_no:Int,guest_name:String,guest_type:String,entry_time:String,guest_mobile_no:Long): Unit ={
    println(s"Hi $first_name for your flat $flat_no has entered $guest_name with $guest_mobile_no as $guest_type at $entry_time at your appartment")
     val params = Map("email" -> "csujanit@gmail.com","password" -> "Wrapper56","device" -> 36875,"number" -> guest_mobile_no,"message"->"small test")

  }

}
