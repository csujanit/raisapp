package rais.rams.raisdbservices

import org.json4s.native.Serialization.{write,writePretty}
import slick.driver.MySQLDriver.api._




/**
 * Created by 866317 on 9/30/2016.
 *
 * */

 import scala.concurrent.{Future, Await}
 import scala.concurrent.duration._

 trait rais_appt_details_query_dao extends raisTables {
   val p: DBIO[Seq[ApptLoginCredsRow]] = app_login_creds result
   type appartment_member_details = (String,String,Number)
   case class appt_mbr_type_row(appt_mbr_fname:String,appt_mbr_lname:String,appt_mbr_id:scala.math.BigInt)
   //val  appt_mbr_type = ApptMemberDetails.map(ls => (ls.apptMbrFirstName,ls.apptMbrLastName,ls.apptMbrId)).filter(ls => (ls._3 != 9603)).result
   val some_test = sql"select appt_mbr_first_name,appt_mbr_last_name,appt_mbr_id from appt_member_details".as[appartment_member_details]
   val appt_mbr_details:DBIO[Seq[ApptMemberDetailsRow]]  = ApptMemberDetails.result
   def getalluser(): List[ApptMemberDetailsRow] = {
       val s = exec(appt_mbr_details)
       s.toList
     }

   }





 object Test extends App with rais_appt_details_query_dao {
   try{
     val s  = exec(some_test)
     println(s)
   }
   finally db.close

 }



