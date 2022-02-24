package sbtz.engine

import com.typesafe.scalalogging.LazyLogging
import sbtz.engine.EngineData.EdgesEnds
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

    nodes.toSeq.flatMap { source =>
      core
        .sources(source)
        .map { case (nTarget, attributes) =>
          DataEdge(core.id2name(source), core.id2name(nTarget), attributes.map(a => EdgeAttribute(a.name, a.value)))
        }
    }
  }

  def upsertEdge(edge: DataEdge): Unit = {
    val sourceNodeId = putName(edge.source)
    val targetNodeId = putName(edge.target)

    val targets = core.sources.getOrElseUpdate(sourceNodeId, EdgesEnds.empty)
    core.sources.getOrElseUpdate(targetNodeId, EdgesEnds.empty)

    targets.update(targetNodeId, edge.attributes)
    core.sources.update(sourceNodeId, targets)
  }

  private def putName(name: String): Long = core.name2id.get(name) match {
    case Some(nodeId) => nodeId
    case None =>
      val nodeId = core.id2name.keySet.max + 1L
      logger.trace(s"putName: added node: ($name, $nodeId)")
      core.id2name.update(nodeId, name)
      core.name2id.update(name, nodeId)
      nodeId
  }
}

object Engine {
  def apply(core: EngineData): Engine = new Engine(core)
}
