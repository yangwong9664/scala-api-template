package models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object ImplicitJsonConversions extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val mailFormat: RootJsonFormat[Test] = jsonFormat1(Test)
}
