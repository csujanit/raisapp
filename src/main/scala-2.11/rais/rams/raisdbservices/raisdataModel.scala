package rais.rams.raisdbservices

/**
 * Created by 866317 on 10/4/2016.
 */

import slick.codegen.SourceCodeGenerator
import slick.jdbc.meta._
import slick.driver.MySQLDriver.api._
import slick.driver.MySQLDriver
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


object raisdataModel extends  App{
 override  def main(args:Array[String]):Unit ={
   val db = Database.forConfig("mysqlDB")
   val tablesviews = MTable.getTables(None,None,None,Some(Seq("TABLE","VIEW")))
   val modelAction = MySQLDriver.createModel(Some(tablesviews))
   val modelFuture = db.run(modelAction)
   println(modelFuture)
   val codgenFuture = modelFuture.map(model => new SourceCodeGenerator(model))
   val path = getClass.getResource("").getPath
   println(path)
   Await.ready(
        codgenFuture.map(_.writeToFile("slick.driver.MySQLDriver",path,"dal","Tables","Tables.scala")),20 seconds)
 }
}
