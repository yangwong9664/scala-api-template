package helpers

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import app.Akka._
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.stubbing.StubMapping

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

trait HttpHelper {

  def statusOf(res: Future[HttpResponse])(implicit timeout: Duration): Int = Await.result(res, timeout).status.intValue()

  def buildPostClient(path: String, body: String): Future[HttpResponse] =
    Http().singleRequest(
      HttpRequest(
        HttpMethods.POST,
        uri = path,
        entity = HttpEntity(ContentTypes.`application/json`, body))
    )

  def buildPutClient(path: String, body: String): Future[HttpResponse] =
    Http().singleRequest(
      HttpRequest(
        HttpMethods.PUT,
        uri = path,
        entity = HttpEntity(ContentTypes.`application/json`, body))
    )

  def buildGetClient(path: String): Future[HttpResponse] =
    Http().singleRequest(
      HttpRequest(
        HttpMethods.GET,
        uri = path)
    )

  def buildPatchClient(path: String): Future[HttpResponse] =
    Http().singleRequest(
      HttpRequest(
        HttpMethods.PATCH,
        uri = path)
    )

  def buildDeleteClient(path: String): Future[HttpResponse] = {
    Http().singleRequest(
      HttpRequest(
        HttpMethods.DELETE,
        uri = path
      )
    )
  }

  def stubPost(url: String, status: StatusCode, responseBody: String): StubMapping =
    stubFor(post(urlMatching(url))
      .willReturn(
        aResponse().
          withStatus(status.intValue())
          .withHeader("Content-Type", "application/json")
          .withBody(responseBody)
      )
    )

  def stubPost(url: String, status: StatusCode): StubMapping =
    stubFor(post(urlMatching(url))
      .willReturn(
        aResponse().
          withStatus(status.intValue())
      )
    )

  def stubGet(url: String, status: StatusCode, responseBody: String): StubMapping =
    stubFor(get(urlMatching(url))
      .willReturn(
        aResponse().
          withStatus(status.intValue())
          .withHeader("Content-Type", "application/json")
          .withBody(responseBody)
      )
    )

  def stubGet(url: String, status: StatusCode): StubMapping =
    stubFor(get(urlMatching(url))
      .willReturn(
        aResponse().
          withStatus(status.intValue())
      )
    )

  def stubDelete(url: String, status: StatusCode): StubMapping =
    stubFor(delete(urlMatching(url))
      .willReturn(
        aResponse().
          withStatus(status.intValue())
      )
    )

  def stubDelete(url: String, status: StatusCode,  responseBody: String): StubMapping =
    stubFor(delete(urlMatching(url))
      .willReturn(
        aResponse().
          withStatus(status.intValue())
          .withHeader("Content-Type", "application/json")
          .withBody(responseBody)
      )
    )

}
