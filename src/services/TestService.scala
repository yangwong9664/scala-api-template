package services

import connectors.TestConnector

import javax.inject.Inject
import scala.concurrent.Future

class TestService @Inject()(testConnector: TestConnector) {

  def test: Future[String] = testConnector.test
}
