package routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.unmarshalling.Unmarshal
import helpers.AkkaTest._
import helpers.IntegrationSpecBase
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random
class HelloISpec extends AnyWordSpec with IntegrationSpecBase {

  override val port: Int = 2000 + Random.nextInt(999)

  "hello" should {
    "return 200 given a success" in {
      stubGet("/activity", StatusCodes.OK, """{"activity":"eating"}""")

      val res = buildGetClient(s"http://localhost:${port}/hello")
      whenReady(res) { result =>
//        result.discardEntityBytes()
        whenReady(Unmarshal(result.entity).to[String]) { entity =>
          println(entity)
          entity.length should be > 0
        }
        result.status.intValue() shouldBe 200
      }
    }
  }

}
