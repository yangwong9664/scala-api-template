package services

import fixture.TestFixture
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}

class TemplateSpec extends AsyncWordSpec with Matchers with TestFixture with ScalaFutures
  with IntegrationPatience with BeforeAndAfterEach with BeforeAndAfterAll{

  override protected def beforeEach(): Unit = {
    super.beforeEach()
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
  }

}
