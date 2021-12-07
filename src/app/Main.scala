package app

import akka.http.scaladsl.Http
import com.google.inject.{Guice, Injector}
import module.GuiceModule
import routes.Routes
import utils.Log._
import app.Akka._

import scala.concurrent.ExecutionContext

object Main extends MainApp

trait MainApp {

  lazy val injector: Injector = Guice.createInjector(new GuiceModule)
  implicit lazy val ec: ExecutionContext = injector.getInstance(classOf[ExecutionContext])
  lazy val route: Routes = injector.getInstance(classOf[Routes])
  lazy val appConfig: AppConfig = injector.getInstance(classOf[AppConfig])
  lazy val httpPort: Int = appConfig.httpPort
  lazy val httpHost: String = appConfig.httpHost

  def main(args: Array[String]): Unit = {
    logInfo(s"Server now online. Please navigate to http://localhost:${httpPort}/hello")
    Http().newServerAt(httpHost, httpPort).bind(route.routes)
  }
}
