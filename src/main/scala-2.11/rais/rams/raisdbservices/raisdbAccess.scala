package rais.rams.raisdbservices



import slick.dbio.DBIO

import scala.concurrent.Await
import slick.driver.MySQLDriver.api._
import scala.concurrent.duration._
import java.sql.DriverManager
import org.jooq._
import org.jooq.impl.DSL
import org.jooq.impl.DSL._
import org.jooq.scala.Conversions._
import org.jooq.impl._
import org.jooq.DSLContext._
/**
 * Created by 866317 on 10/6/2016.
 */
trait raisdbAccess {
  val db = Database.forConfig("mysqlDB")
  def exec[T](program:DBIO[T]):T = Await.result(db.run(program),200 seconds)
  //def exec[T](program:DBIO[T]):T = db.run(program).asInstanceOf[T]
  val c = DriverManager.getConnection("jdbc:mysql://localhost:3311/app_base","root","root")
  val create:DSLContext = DSL.using(c,SQLDialect.MYSQL)
//  val s = create.selectFrom(AppDetails).fetch().formatJSON()
//  val fs = (i:Int) => String = s"$i"
}


