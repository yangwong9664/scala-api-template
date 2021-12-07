package utils

import com.typesafe.scalalogging.Logger
import sourcecode.{File, Line}

import scala.util.{Failure, Success, Try}

object Log {
  private val logger: Logger = Logger("scala-api-template")

  def logInfo(arg: String)(implicit line: Line, file: File): Unit = {
    logger.info(s"$arg, file: ${fileName(file.value)}, line: ${line.value}")
  }

  def logError(arg: String)(implicit line: Line, file: File): Unit = {
    logger.error(s"$arg, file: ${fileName(file.value)}, line: ${line.value}")
  }

  def logWarn(arg: String)(implicit line: Line, file: File): Unit = {
    logger.warn(s"$arg, file: ${fileName(file.value)}, line: ${line.value}")
  }

  private def fileName(path: String) = Try {
    path.split("src/").last
  } match {
    case Success(value) => value
    case Failure(_) => "unknown"
  }
}
