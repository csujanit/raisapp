package domain

/**
 * Created by 866317 on 12/26/2016.
 */
import java.sql.{ResultSet, Timestamp}
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime
import org.mindrot.jbcrypt.BCrypt
import java.util.UUID.randomUUID
import scala.concurrent.{Future, ExecutionContext}
import scala.concurrent.ExecutionContext.Implicits._
import domain.UserRepo.userFromRowData
import db.DB.execute

case class LoginToken(token:String,modified:DateTime,created:DateTime)

object LoginToken extends LazyLogging{

  implicit val executionContext:ExecutionContext = global

  def authenticateUser(username:String,password:String):Future[Option[User]] = {
    logger.info(s"Attempting to log user with username:$username")
    execute("SELECT * FROM users where username=? LIMIT 1",username)map{
      data =>
        data.rows match{
          case Some(resultSet) =>
            resultSet.length match{
              case 0 => logger.info(s"Could not find user:$username")
                        None
              case 1 => val user = userFromRowData(resultSet.head)
                        BCrypt.checkpw(password,user.password) match {
                          case true => logger.info(s"logger successfully logged with $username ")
                                       Some(user)
                          case false => None
                        }
              case _ => None
            }

          case _ => None
        }
    }
  }

  def generateLoginToken(username:String,password:String) :Future[Option[LoginToken]] = {
    authenticateUser(username,password).flatMap{
      case Some(user) =>
           val created = DateTime.now
           val token = LoginToken(randomUUID.toString,created,created)
           execute("INSERT INTO login_tokens(token,user_id,last_used,created,modified) values(?,?,?,?,?)",
           randomUUID().toString,
           user.id,
           new Timestamp(token.created.getMillis),
           new Timestamp(token.created.getMillis),
           new Timestamp(token.created.getMillis)
           ) map{
             data =>
               data.rows match {
                 case Some(resultSet) => Some(token)
                 case _ => None
               }
           }
      case _ => Future(None)
    }
  }
}
