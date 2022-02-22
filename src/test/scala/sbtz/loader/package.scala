package sbtz

import sbtz.engine.EngineData

package object loader {
  def loadT: Either[Throwable, LoadedData] = LoadedData.load("case_test.csv", cutHeader = true)
  def load: Either[String, LoadedData] = loadT.left.map(th => Option(th.getMessage).getOrElse(th.toString))

  def contaminate(data: LoadedData): LoadedData = data.lines match {
    case seq if seq.isEmpty => data
    case head +: tail       => new LoadedData(data.header, contaminate(head) +: tail)
  }

  def contaminate(s: String): String = {
    val cols = s.split(',')
    cols(0) = ""
    cols(1) = ""
    cols.mkString(",")
  }

  def loadEngineData: Either[String, EngineData] = for {
    data <- load
    edges <- DataEdge.parse(data).toEither
  } yield EngineData(edges)
}
