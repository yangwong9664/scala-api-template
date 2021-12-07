package routes

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives.{path, _}
import akka.http.scaladsl.server._
import services.TestService

import javax.inject.{Inject, Singleton}
import scala.util.Failure
import scala.util.Success

@Singleton
class Routes @Inject() (testService: TestService) {

  lazy val routes: Route = {
    path("hello"){
      get {
        onComplete(testService.test) {
          case Success(res) => complete(OK, res)
          case Failure(_) => complete(InternalServerError)
        }
      }
    }
  }

}
