package utils

import com.typesafe.config.ConfigFactory

/**
 * Created by 866317 on 12/28/2016.
 */
trait Config {
   private val config = ConfigFactory.load()
//   private val httpConfig = config.getString("http")
   private val databaseConfig = config.getConfig("database")
   val databaseUrl      = databaseConfig.getString("url")
   val databaseUser     = databaseConfig.getString("user")
   val databasePassword = databaseConfig.getString("password")
   val databaseName = databaseConfig.getString("name")
   val databasePort = databaseConfig.getInt("port")
   val databaseHost = databaseConfig.getString("host")
   val databasePoolMaxObjects = databaseConfig.getInt("maxObjects")
   val databasePoolMaxIdle = databaseConfig.getInt("maxIdle")
   val databasePoolMaxSize = databaseConfig.getInt("maxQueueSize")
}
