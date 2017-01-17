/**
  * Created by crramya on 09/01/17.
  */
import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.github.mauricio.async.db.postgresql.util.URLParser
import com.github.mauricio.async.db.util.ExecutorServiceUtils.CachedExecutionContext
import com.github.mauricio.async.db.{RowData, QueryResult, Connection}
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


    object BasicExample {

      def main(args: Array[String]) {

//        val configuration = URLParser.parse("jdbc:postgresql://localhost:5233/my_database?user=postgres&password=somepassword")

        val configuration = URLParser.parse("jdbc:postgresql://elmer.db.elephantsql.com:5432/twyvjytz?user=twyvjytz&password=LSNvxOcVwg4i-6hJxCZE2fGN2QVxFFnx")

        val connection: Connection = new PostgreSQLConnection(configuration)

        Await.result(connection.connect, 5 seconds)

        val future: Future[QueryResult] = connection.sendQuery("SELECT * FROM FLATS WHERE FLAT_NO = 110 ")

        val mapResult: Future[Any] = future.map(queryResult => queryResult.rows match {
          case Some(resultSet) => {
            val row : RowData = resultSet.head
            row(0)
          }
          case None => -1
        }
        )

        val result = Await.result( mapResult, 5 seconds )

        println(result)

        connection.disconnect

      }

    }

