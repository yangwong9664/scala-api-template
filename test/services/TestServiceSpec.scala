package services

import fixture.TestFixture

import scala.concurrent.Future

class TestServiceSpec extends TestFixture {

  "test" should {
    "return a string when a successful" in {
      (mockTestConnector.test _).expects().returning(Future.successful("test"))

      whenReady(testService.test) { res =>
        res shouldBe "test"
      }
    }
  }

}
