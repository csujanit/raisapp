package domain

import com.github.mauricio.async.db.RowData

/**
 * Created by 866317 on 12/28/2016.
 */
case class FlatUser(flat_no:Int,first_name:String,last_name:String,mobile_no:Long,email_id:String,active_status:String)

object FlatUser {
  def flatuserFromRowData(rowData: RowData):FlatUser = {
    FlatUser(
//      rowData.apply("row_id").asInstanceOf[String],
      rowData.apply("flat_no").toString.toInt,
      rowData.apply("first_name").toString,
      rowData.apply("last_name").toString,
      rowData.apply("mobile_no").toString.toLong,
      rowData.apply("email_id").toString,
      rowData.apply("active_status").toString
            )
  }
}


