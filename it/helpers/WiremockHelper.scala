package helpers

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import org.scalatest.concurrent.{Eventually, IntegrationPatience}
import org.scalatest.time.{Millis, Seconds, Span}

trait WiremockHelper extends Eventually with IntegrationPatience {

  implicit val defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(100, Seconds), interval = Span(100, Millis))

  lazy val wmConfig: WireMockConfiguration = wireMockConfig().port(11111).bindAddress("localhost")
  lazy val wiremockServer = new WireMockServer(wmConfig)

  def startWiremock(): Unit = {
    WireMock.configureFor("localhost", 11111)
    wiremockServer.start()
  }

  def stopWiremock(): Unit = {
    wiremockServer.stop()
  }

  def resetWiremock(): Unit = WireMock.reset()
}
