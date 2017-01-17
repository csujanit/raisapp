/**
 * Created by 866317 on 12/28/2016.
 */
package db

import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.mysql.pool.MySQLConnectionFactory
import com.github.mauricio.async.db.pool.{ConnectionPool, PoolConfiguration}
import com.github.mauricio.async.db.postgresql.pool.PostgreSQLConnectionFactory
import com.github.mauricio.async.db.postgresql.util.URLParser
import utils.Config



class Pool  extends Config {
  /* val configuration = new Configuration(
        username = "root",
        host = "localhost",
        port = 3311,
        password = Some(databasePassword),
        database = Some(databaseName))
*/
//   val configuration = URLParser.parse("jdbc:postgres://twyvjytz:LSNvxOcVwg4i-6hJxCZE2fGN2QVxFFnx@elmer.db.elephantsql.com:5432/twyvjytz")

   val configuration = URLParser.parse("jdbc:postgresql://elmer.db.elephantsql.com:5432/twyvjytz?user=twyvjytz&password=LSNvxOcVwg4i-6hJxCZE2fGN2QVxFFnx")

   val factory = new PostgreSQLConnectionFactory(configuration)
   val pool = new ConnectionPool(factory,new PoolConfiguration(100,10,5000))
}