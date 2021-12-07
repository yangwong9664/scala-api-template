package routes

import akka.http.scaladsl.model._
import fixture.TestFixture

import scala.concurrent.Future

class RoutesSpec extends TestFixture {

  "/hello" should {
    "return a 200 when a successful" in {
      (mockTestService.test _).expects().returning(Future.successful(""))

      HttpRequest(HttpMethods.GET, uri = "/hello") ~> routes.routes ~> check {
        response.status shouldBe StatusCodes.OK
      }
    }

    "return a 500 when a unsuccessful" in {
      (mockTestService.test _).expects().returning(Future.failed(new Exception))

      HttpRequest(HttpMethods.GET, uri = "/hello") ~> routes.routes ~> check {
        response.status shouldBe StatusCodes.InternalServerError
      }
    }
  }

}
