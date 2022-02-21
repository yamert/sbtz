package example

import java.io.File

class FileData(val lines: IndexedSeq[String])

object FileData {

  def load(path: String): FileData = {
    val lines = scala.io.Source.fromFile(new File(path), 1024 * 1024).getLines().map (s => s.trim).filter(_.nonEmpty).toIndexedSeq

    new FileData(lines)
  }
}
