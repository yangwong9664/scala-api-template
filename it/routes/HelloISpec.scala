package routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.unmarshalling.Unmarshal
import app.Akka._
import helpers.IntegrationSpecBase
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random

class HelloISpec extends AnyWordSpec with IntegrationSpecBase {

  override val port: Int = 2000 + Random.nextInt(999)

  //TODO make stub work
//  "hello" should {
//    "return 200 given a success" in {
//      stubGet("/api/activity", StatusCodes.OK, """{"activity":"eating"}""")
//
//      val res = buildGetClient(s"http://localhost:${port}/hello")
//      whenReady(res) { result =>
//        whenReady(Unmarshal(result.entity).to[String]) { entity =>
//          entity shouldBe "eating"
//        }
//        result.status.intValue() shouldBe 200
//      }
//    }
//  }

}
