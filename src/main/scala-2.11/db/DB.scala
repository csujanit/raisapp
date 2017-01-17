package db

/**
 * Created by 866317 on 12/28/2016.
 */

import com.github.mauricio.async.db.{RowData, QueryResult}

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.{ExecutionContext, Future}

object DB {
 implicit  val executionContext:ExecutionContext = global
 lazy val pool = new Pool().pool
 def execute(query:String,values:Any*):Future[QueryResult] = {
   println(pool.isConnected)
   if(values.nonEmpty){
     pool.sendPreparedStatement(query,values);
   }
   else
     pool.sendQuery(query)
 }
 def fetch(query:String,values:Any*):Future[Option[Seq[RowData]]] = execute(query,values:_*).map(_.rows)
}
