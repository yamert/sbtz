package sbtz.loader

import cats.data.Validated
import cats.data.ValidatedNel
import cats.implicits._

// TODO: rename to Edge
final case class DataEdge(
  source: String,
  target: String,
  attributes: Seq[EdgeAttribute]
)

object DataEdge {

  type ErrOf[A] = Validated[String, A]
  type ErrNelOf[A] = ValidatedNel[String, A]

  def parse(line: String): ErrNelOf[DataEdge] = validateParts(line.split(','))

  def parse(lines: Seq[String]): ErrOf[Seq[DataEdge]] = {
    val (edges, errors) = lines.map(parse).foldLeft((Seq.empty[DataEdge], Seq.empty[String])) {
      case ((accEdg, accErr), Validated.Valid(edge)) => (edge +: accEdg, accErr)
      case ((accEdg, accErr), Validated.Invalid(nel)) =>
        val updErr = if (accErr.nonEmpty) accErr else nel.toList.mkString("\n") +: accErr
        (accEdg, updErr)
    }

    errors.headOption.fold(edges.valid[String])(_.invalid)
  }

  private def validateParts(columns: Array[String]): ErrNelOf[DataEdge] = Validated
    .condNel(columns.length == 4, columns, "Invalid columns count in line, should be 4")
    .andThen { columns =>
      (
        validateNodeName(columns(0)),
        validateNodeName(columns(1)),
        columns(2).valid,
        columns(3).valid
      ).mapN((node1, node2, attr1, attr2) =>
        DataEdge(node1, node2, Seq(EdgeAttribute("type", attr1), EdgeAttribute("cases", attr2)))
      )
    }

  private def validateNodeName(s: String): ErrNelOf[String] = Validated.condNel(s.nonEmpty, s, "Empty 'node id' string")

  def parse(loadedData: LoadedData): ErrOf[Seq[DataEdge]] = parse(loadedData.lines)
}

final case class EdgeAttribute(name: String, value: String)
