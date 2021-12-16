package helpers

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

trait WiremockHelper {

  lazy val wmConfig: WireMockConfiguration = wireMockConfig().port(11111).bindAddress("localhost")
  lazy val wiremockServer = new WireMockServer(wmConfig)

  def startWiremock(): Unit = {
    wiremockServer.start()
    WireMock.configureFor("localhost", 11111)
  }

  def stopWiremock(): Unit = {
    wiremockServer.stop()
  }

  def resetWiremock(): Unit = WireMock.reset()
}
