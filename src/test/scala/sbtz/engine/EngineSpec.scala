package sbtz.engine

import org.scalatest.EitherValues
import org.scalatest.OptionValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import sbtz.loader
import sbtz.loader.DataEdge
import sbtz.loader.EdgeAttribute

class EngineSpec extends AnyFlatSpec with should.Matchers with EitherValues with OptionValues {

  import EngineSpec._

  "engine" should "get N items by hop 0" in {
    val engine = loadEngine
    val depth = 0L

    val actual1 = engine.getInDepth("case2_3", depth)
    actual1.size shouldEqual 0L
    actual1 should contain theSameElementsAs Seq.empty[String]

    //

    val expected2 = Seq(DataEdge(c11, c11, Seq(loader.EdgeAttribute("type", "501"), EdgeAttribute("cases", "case1"))))
    val actual2 = engine.getInDepth("case1_1", depth)
    actual2.size shouldEqual 1L
    actual2 should contain theSameElementsAs expected2

    //

    val expected3 = Seq(
      DataEdge(c31, c31, Seq(loader.EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c31, c32, Seq(loader.EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c31, c33, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c31, c34, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3")))
    )

    val actual3 = engine.getInDepth(c31, depth)
    actual3.size shouldEqual 4L
    actual3 should contain theSameElementsAs expected3
  }

  "engine" should "get N items by hop 1" in {
    val engine = loadEngine
    val depth = 1L

    val actual1 = engine.getInDepth("case2_3", depth)
    actual1.size shouldEqual 0L
    actual1 should contain theSameElementsAs Seq.empty[String]

    //

    val expected2 = Seq(DataEdge(c11, c11, Seq(loader.EdgeAttribute("type", "501"), EdgeAttribute("cases", "case1"))))
    val actual2 = engine.getInDepth("case1_1", depth)
    actual2.size shouldEqual 1L
    actual2 should contain theSameElementsAs expected2

    //

    val expected3 = Seq(
      DataEdge(c31, c31, Seq(loader.EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c31, c32, Seq(loader.EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c31, c33, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c31, c34, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c32, c33, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c32, c37, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c33, c38, Seq(loader.EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3")))
    )
    val actual3 = engine.getInDepth(c31, depth)
    actual3.size shouldEqual 7L
    actual3 should contain theSameElementsAs expected3
  }

  "engine" should "get N items by hop 2" in {
    val engine = loadEngine
    val depth = 2L

    val actual1 = engine.getInDepth("case2_3", depth)
    actual1.size shouldEqual 0L
    actual1 should contain theSameElementsAs Seq.empty[String]

    //

    val expected2 = Seq(DataEdge(c11, c11, Seq(loader.EdgeAttribute("type", "501"), EdgeAttribute("cases", "case1"))))
    val actual2 = engine.getInDepth("case1_1", depth)
    actual2.size shouldEqual 1L
    actual2 should contain theSameElementsAs expected2

    //

    val expected3 = Seq(
      DataEdge(c31, c31, Seq(loader.EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c31, c32, Seq(loader.EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c31, c33, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c31, c34, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c32, c33, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c32, c37, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c33, c38, Seq(loader.EdgeAttribute("type", "501"), EdgeAttribute("cases", "case3"))),
      DataEdge(c37, c33, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3"))),
      DataEdge(c38, c34, Seq(loader.EdgeAttribute("type", "500"), EdgeAttribute("cases", "case3")))
    )
    val actual3 = engine.getInDepth(c31, depth)
    actual3.size shouldEqual 9L
    actual3 should contain theSameElementsAs expected3
  }
}

object EngineSpec {
  val c11 = "case1_1"
  val (c31, c32, c33, c34, c37, c38) = ("case3_1", "case3_2", "case3_3", "case3_4", "case3_7", "case3_8")
}
