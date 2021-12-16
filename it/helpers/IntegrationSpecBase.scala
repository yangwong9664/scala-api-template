package helpers

import akka.http.scaladsl.Http
import app.{AppConfig, MainApp}
import com.github.tomakehurst.wiremock.client.WireMock.setGlobalFixedDelay
import com.google.inject.{Guice, Injector}
import database.MongoDB
import app.Akka._
import module.GuiceModule
import org.mongodb.scala.bson.BsonDocument
import org.scalatest._
import org.scalatest.concurrent.{Eventually, IntegrationPatience, ScalaFutures}
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

trait IntegrationSpecBase extends AnyWordSpec
  with GivenWhenThen
  with TestSuite
  with ScalaFutures
  with IntegrationPatience
  with Matchers
  with BeforeAndAfterEach
  with BeforeAndAfterAll
  with Eventually
  with DataOperations
  with WiremockHelper
  with HttpHelper {

  implicit val defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(100, Seconds), interval = Span(100, Millis))
  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global

  val port: Int

  lazy val testInjector: Injector = Guice.createInjector(new GuiceModule {
    override def configure(): Unit = {
      bind(classOf[AppConfig]) to classOf[WiremockAppConfig]
    }
  })

  implicit lazy val app: MainApp = new MainApp {
    override lazy val injector: Injector = testInjector
    override lazy val httpPort: Int = port
  }

  override protected def mongoDB: MongoDB = new MongoDB(new WiremockAppConfig)

  def setDelay(amount: FiniteDuration): Unit = setGlobalFixedDelay(amount.toMillis.toInt)

  def shutdownSystem: Future[Unit] = Http().shutdownAllConnectionPools() andThen {case _ => system.terminate()}

  override def beforeEach(): Unit = {
    Await.result(mongoDB.metadataCollection.deleteMany(BsonDocument()).toFuture(), 10.seconds)
    super.beforeEach()
  }

  override def beforeAll(): Unit = {
    app.main(Array.empty)
    startWiremock()
    super.beforeAll()
  }

  override def afterEach(): Unit = {
    super.afterEach()
  }

  override def afterAll(): Unit = {
    Await.result(shutdownSystem, 10.seconds)
    Await.result(mongoDB.metadataCollection.deleteMany(BsonDocument()).toFuture(), 10.seconds)
    stopWiremock()
    super.afterAll()
  }

}
