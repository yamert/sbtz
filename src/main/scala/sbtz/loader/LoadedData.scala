package sbtz.loader

import java.io.File
import scala.io.Codec
import scala.io.Source
import scala.util.Using

class LoadedData(
  val header: Option[String],
  val lines: Seq[String]
)

object LoadedData {

  implicit val codecUtf8: Codec = scala.io.Codec.UTF8
  private val bufferSize = 1024 * 1024

  def load(path: String, cutHeader: Boolean): Either[Throwable, LoadedData] =
    Using(Source.fromFile(new File(path), bufferSize)) { src =>
      val iter = src
        .getLines()
        .map(_.trim)
        .filter(_.nonEmpty)

      val header = if (cutHeader) iter.nextOption() else None
      val lines = iter.toSeq

      new LoadedData(header, lines)
    }.toEither
}
