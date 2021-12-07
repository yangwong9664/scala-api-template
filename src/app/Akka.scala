package app

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor

object Akka {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext
  implicit val materializer: Materializer = Materializer.createMaterializer(system)
}
