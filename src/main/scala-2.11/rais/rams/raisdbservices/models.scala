
package rais.rams.raisdbservices

import slick.driver.MySQLDriver.api._




/**
 * Created by 866317 on 9/30/2016.
 *
 * */

import scala.concurrent.Await
import scala.concurrent.duration._

trait raisTables extends raisdbAccess {
  case class ApptLoginCredsRow(apptId: Int, apptLoginMbrId: Int, apptMbrType: Char, apptMbrPassword: String, apptPwdChange: Char)
  class ApptLoginCreds(_tableTag: Tag) extends Table[ApptLoginCredsRow](_tableTag, "appt_login_creds") {
    def * = (apptId, apptLoginMbrId, apptMbrType, apptMbrPassword, apptPwdChange) <>(ApptLoginCredsRow.tupled, ApptLoginCredsRow.unapply)
    val apptId = column[Int]("appt_id")
    val apptLoginMbrId = column[Int]("appt_login_mbr_id")
    val apptMbrType = column[Char]("appt_mbr_type")
    val apptMbrPassword = column[String]("appt_mbr_password")
    val apptPwdChange = column[Char]("appt_pwd_change")
  }
  lazy val app_login_creds = TableQuery[ApptLoginCreds]

  case class ApptMbrDetailsRow(apptId: Int, apptLoginMbrId: Int, apptMbrFname:String, apptMbrLname:String)
  class ApptMbrDetails(_tableTag: Tag) extends Table[ApptMbrDetailsRow](_tableTag, "appt_mbr_details") {
    def * = (apptId, apptLoginMbrId, apptMbrFname, apptMbrLname) <> (ApptMbrDetailsRow.tupled, ApptMbrDetailsRow.unapply)
    val apptId = column[Int]("appt_id")
    val apptLoginMbrId = column[Int]("appt_login_mbr_id")
    val apptMbrFname = column[String]("appt_mbr_fname")
    val apptMbrLname = column[String]("appt_mbr_lname")
    val pk = primaryKey("appt_mbr_details_PK", (apptId, apptLoginMbrId))
    val index1 = index("appt_id", apptId, unique=true)
    val index2 = index("appt_login_mbr_id", apptLoginMbrId, unique=true)
  }
  lazy val ApptMbrDetails =  TableQuery[ApptMbrDetails]

  case class ApptMemberDetailsRow(apptId: String, apptMbrId: scala.math.BigDecimal , apptMbrPwd: String, apptMbrFirstName:String, apptMbrLastName:String, apptMbrLoginType:Char, apptMbrPrflType:String)
  class ApptMemberDetails(_tableTag: Tag) extends Table[ApptMemberDetailsRow](_tableTag, "appt_member_details") {
    def * = (apptId, apptMbrId, apptMbrPwd, apptMbrFirstName, apptMbrLastName, apptMbrLoginType, apptMbrPrflType) <> (ApptMemberDetailsRow.tupled, ApptMemberDetailsRow.unapply)
    val apptId = column[String]("appt_id")
    val apptMbrId = column[scala.math.BigDecimal]("appt_mbr_id")
    val apptMbrPwd = column[String]("appt_mbr_pwd")
    val apptMbrFirstName= column[String]("appt_mbr_first_name")
    val apptMbrLastName = column[String]("appt_mbr_last_name")
    val apptMbrLoginType = column[Char]("appt_mbr_login_type")
    val apptMbrPrflType = column[String]("appt_mbr_prfl_type")
    val pk = primaryKey("appt_member_details_PK", (apptId, apptMbrId))
  }
  lazy val ApptMemberDetails = TableQuery[ApptMemberDetails]


}