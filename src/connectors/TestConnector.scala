package connectors

import akka.http.scaladsl.unmarshalling.Unmarshal
import app.Akka._
import models.ImplicitJsonConversions._
import models.Test

import javax.inject.Inject
import scala.concurrent.Future

class TestConnector @Inject() extends HttpHelper {

  def test: Future[String] = httpGetRequest("https://www.boredapi.com/api/activity").flatMap { res =>
    Unmarshal(res.entity).to[Test].map { res =>
      res.activity
    }
  }
}
