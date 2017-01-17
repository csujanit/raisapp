package domain

import com.github.mauricio.async.db.RowData

/**
 * Created by 866317 on 12/28/2016.
 */
case class User(id:String,username:String,password:String,role:String)

object UserRepo{
  def userFromRowData(rowData: RowData):User = {
    User( rowData.apply("id").asInstanceOf[String],
          rowData.apply("username").asInstanceOf[String],
          rowData.apply("password").asInstanceOf[String],
          rowData.apply("role").asInstanceOf[String]
         )
  }
}