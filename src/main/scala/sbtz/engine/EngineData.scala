package sbtz.engine

import com.typesafe.scalalogging.LazyLogging
import sbtz.engine.EngineData.EdgesEnds
import sbtz.loader.DataEdge
import sbtz.loader.EdgeAttribute

import scala.collection.mutable

class EngineData(
  val sources: mutable.LongMap[EdgesEnds],
  val name2id: Map[String, Long],
  val id2name: mutable.LongMap[String]
)

object EngineData extends LazyLogging {

  type EdgesEnds = mutable.LongMap[Seq[Attribute]]
  object EdgesEnds { def empty: EdgesEnds = new mutable.LongMap[Seq[Attribute]] }

  def apply(edges: Seq[DataEdge]): EngineData = {
    val namesIndexed: Seq[(String, Long)] = edges
      .flatMap(edge => Seq(edge.target, edge.source))
      .distinct
      .sorted
      .zipWithIndex
      .map { case (name, idx) => (name, idx.toLong) }

    val name2id: Map[String, Long] = namesIndexed.toMap
    def id2name: mutable.LongMap[String] = mutable.LongMap.from(name2id.map { case (name, idx) => (idx, name) })

    val sources: mutable.LongMap[EdgesEnds] = edges.foldLeft(mutable.LongMap.empty[EdgesEnds]) { case (acc, edge) =>
      val sourceId = name2id(edge.source)
      val targetId = name2id(edge.target)

      val targets = acc.getOrElseUpdate(sourceId, EdgesEnds.empty)
      acc.getOrElseUpdate(targetId, EdgesEnds.empty)

      targets.put(targetId, edge.attributes.map(Attribute(_)))
      acc.put(sourceId, targets)
      acc
    }

    new EngineData(sources, name2id, id2name)
  }
}

final case class Attribute(name: String, value: String)

object Attribute {
  def apply(a: EdgeAttribute): Attribute = Attribute(a.name, a.value)
}
