package sbtz.engine

import org.scalatest.EitherValues
import org.scalatest.OptionValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import sbtz.loader.DataEdge
import sbtz.loader.EdgeAttribute

class EngineUpsertSpec extends AnyFlatSpec with should.Matchers with EitherValues with OptionValues {

  "updated engine, added separated 1 edge self->self, depth 0" should "see 1 edge" in {
    val depth = 0L
    val edge = DataEdge(c99_1, c99_1, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case99")))

    val engine = loadEngine
    engine.upsertEdge(edge)

    engine.getInDepth(c99_1, depth) should contain theSameElementsAs Seq(edge)
  }

  "updated engine, added separated 2 edges 1->1->1, depth 0" should "see 1 edge" in {
    val depth = 0L
    val edge1 = DataEdge(c99_1, c99_2, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case99")))
    val edge2 = DataEdge(c99_2, c99_3, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case99")))

    val engine = loadEngine
    engine.upsertEdge(edge1)
    engine.upsertEdge(edge2)

    engine.getInDepth(c99_1, depth) should contain theSameElementsAs Seq(edge1)
  }

  "updated engine, added separated 2 edges 1->1->1, depth 1" should "see 2 edge" in {
    val depth = 1L
    val edge1 = DataEdge(c99_1, c99_2, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case99")))
    val edge2 = DataEdge(c99_2, c99_3, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case99")))

    val engine = loadEngine
    engine.upsertEdge(edge1)
    engine.upsertEdge(edge2)

    engine.getInDepth(c99_1, depth) should contain theSameElementsAs Seq(edge1, edge2)
  }

  "updated engine, added separated 2 edges 1->1->1, depth 2" should "see 2 edge" in {
    val depth = 2L
    val edge1 = DataEdge(c99_1, c99_2, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case99")))
    val edge2 = DataEdge(c99_2, c99_3, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case99")))

    val engine = loadEngine
    engine.upsertEdge(edge1)
    engine.upsertEdge(edge2)

    engine.getInDepth(c99_1, depth) should contain theSameElementsAs Seq(edge1, edge2)
  }

  "updated engine, added 1 edge to existing, depth 0" should "see 2 edges" in {
    val depth = 0L
    val edge = DataEdge(c1_1, c1_99, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case1_99")))

    val engine = loadEngine
    engine.upsertEdge(edge)

    val expected = Seq(DataEdge(c1_1, c1_1, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case1"))), edge)
    engine.getInDepth(c1_1, depth) should contain theSameElementsAs expected
  }
}
