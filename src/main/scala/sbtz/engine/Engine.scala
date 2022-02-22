package sbtz.engine

import com.typesafe.scalalogging.LazyLogging
import sbtz.loader.DataEdge
import sbtz.loader.EdgeAttribute

import scala.annotation.tailrec

class Engine private (private val core: EngineData) extends LazyLogging {

  def getInDepth(startNode: String, depth: Long): Seq[DataEdge] = {
    logger.trace(s"getInDepth : startNode=$startNode depth=$depth")

    verboseNodes(core.name2id.get(startNode) match {
      case Some(node) => process(depth, Set.empty, Set(node))
      case None       => Set.empty[Long]
    })
  }

  @tailrec
  private def process(hop: Long, visited: Set[Long], notVisited: Set[Long]): Set[Long] = {
    logger.trace(s"process: hop=$hop visited=$visited notVisited=$notVisited")

    if (hop < 0 || notVisited.isEmpty) {
      visited
    } else {
      val nextNotVisited = notVisited.toSeq.flatMap(core.sources.get).flatMap(_.keySet.toSet).toSet
      val mustVisitNodes = nextNotVisited.diff(visited)
      process(hop - 1, visited ++ notVisited, mustVisitNodes)
    }
  }

  private def verboseNodes(nodes: Set[Long]): Seq[DataEdge] = {
    logger.trace(s"verboseNodes: sources=$nodes")

    nodes.toSeq.flatMap { nSource =>
      core
        .sources(nSource)
        .map { case (nTarget, attributes) =>
          DataEdge(core.id2name(nSource), core.id2name(nTarget), attributes.map(a => EdgeAttribute(a.name, a.value)))
        }
    }
  }
}

object Engine {
  def apply(core: EngineData): Engine = new Engine(core)
}
