package sbtz.engine

import org.scalatest.EitherValues
import org.scalatest.OptionValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import sbtz.loader.DataEdge
import sbtz.loader.EdgeAttribute

class EngineSpec extends AnyFlatSpec with should.Matchers with EitherValues with OptionValues {

  "depth 0" should "see 0 edges ->1" in {
    val engine = loadEngine
    val depth = 0L

    val actual1 = engine.getInDepth(c2_3, depth)
    actual1.size shouldEqual 0L
    actual1 should contain theSameElementsAs Seq.empty[String]
  }

  "depth 0" should "see 1 edge self->self" in {
    val engine = loadEngine
    val depth = 0L

    val expected2 = Seq(DataEdge(c1_1, c1_1, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case1"))))
    val actual2 = engine.getInDepth(c1_1, depth)
    actual2.size shouldEqual 1L
    actual2 should contain theSameElementsAs expected2
  }

  "depth 0" should "see 4 edges, 1->4" in {
    val engine = loadEngine
    val depth = 0L

    val expected3 = Seq(
      DataEdge(c3_1, c3_1, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_1, c3_2, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_1, c3_3, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_1, c3_4, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3")))
    )

    val actual3 = engine.getInDepth(c3_1, depth)
    actual3.size shouldEqual 4L
    actual3 should contain theSameElementsAs expected3
  }

  "depth 1" should "see 0 edges ->1" in {
    val engine = loadEngine
    val depth = 1L

    val actual1 = engine.getInDepth(c2_3, depth)
    actual1.size shouldEqual 0L
    actual1 should contain theSameElementsAs Seq.empty[String]
  }

  "depth 1" should "see 1 edge self->self" in {
    val engine = loadEngine
    val depth = 1L

    val expected2 = Seq(DataEdge(c1_1, c1_1, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case1"))))
    val actual2 = engine.getInDepth(c1_1, depth)
    actual2.size shouldEqual 1L
    actual2 should contain theSameElementsAs expected2
  }

  "depth 1" should "see 7 edges, 1->4->7" in {
    val engine = loadEngine
    val depth = 1L

    val expected3 = Seq(
      DataEdge(c3_1, c3_1, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_1, c3_2, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_1, c3_3, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_1, c3_4, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_2, c3_3, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_2, c3_7, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_3, c3_8, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3")))
    )
    val actual3 = engine.getInDepth(c3_1, depth)
    actual3.size shouldEqual 7L
    actual3 should contain theSameElementsAs expected3
  }

  "depth 2" should "see 0 edges ->1" in {
    val engine = loadEngine
    val depth = 2L

    val actual = engine.getInDepth(c2_3, depth)
    actual.size shouldEqual 0L
    actual should contain theSameElementsAs Seq.empty[String]
  }

  "depth 2" should "see 1 edges self->self" in {
    val engine = loadEngine
    val depth = 2L

    val expected2 = Seq(DataEdge(c1_1, c1_1, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case1"))))
    val actual2 = engine.getInDepth(c1_1, depth)
    actual2.size shouldEqual 1L
    actual2 should contain theSameElementsAs expected2
  }

  "depth 2" should "see 9 edges 1->4->9" in {
    val engine = loadEngine
    val depth = 2L

    val expected3 = Seq(
      DataEdge(c3_1, c3_1, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_1, c3_2, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_1, c3_3, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_1, c3_4, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_2, c3_3, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_2, c3_7, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_3, c3_8, Seq(EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_7, c3_3, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c3_8, c3_4, Seq(EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3")))
    )
    val actual3 = engine.getInDepth(c3_1, depth)
    actual3.size shouldEqual 9L
    actual3 should contain theSameElementsAs expected3
  }
}
