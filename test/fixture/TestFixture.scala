package fixture

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest, HttpResponse}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import app.ApplicationConfig
import cats.data.EitherT
import com.google.inject.{Guice, Injector}
import connectors.TestConnector
import errors.AppErrors
import module.GuiceModule
import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Seconds, Span}
import org.scalatest.wordspec._
import routes.Routes
import services._

import scala.concurrent.Future

trait TestFixture extends AsyncWordSpec with AsyncMockFactory with Matchers with ScalatestRouteTest with ScalaFutures {

  implicit val defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(5, Seconds), interval = Span(0.1, Seconds))

  val injector: Injector = Guice.createInjector(new GuiceModule)

  val appConfig: ApplicationConfig = injector.getInstance(classOf[ApplicationConfig])

  val mockTestService: TestService = mock[TestService]

  val mockTestConnector: TestConnector = mock[TestConnector]

  val routes: Routes = new Routes(mockTestService)

  val testService: TestService = new TestService(mockTestConnector)

  def testConnector(status: Option[Int], body: Option[String]): TestConnector = new TestConnector {
    override def singleRequest(request: HttpRequest): Future[HttpResponse] = status match {
      case Some(code) if body.isDefined => Future(HttpResponse(
        code, entity = HttpEntity(ContentTypes.`application/json`, body.getOrElse(""))))
      case Some(code) => Future(HttpResponse(code))
      case _ => Future.failed(new Exception)
    }
  }

  def createResultType[T](model: T): Result[T] = EitherT.right(Future(model))

  def createResultTypeChazError[T](error: AppErrors): Result[T] = EitherT.left(Future(error))

  def createResultException[T]: Result[T] = EitherT.right(Future.failed(new Exception("error")))

}
