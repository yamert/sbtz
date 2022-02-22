package sbtz.engine

import org.scalatest.EitherValues
import org.scalatest.OptionValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import sbtz.loader.loadEngineData

class EngineDataSpec extends AnyFlatSpec with should.Matchers with EitherValues with OptionValues {

  "engine data internal collections" should "have same sizes" in {
    loadEngineData.value.sources.size shouldEqual loadEngineData.value.id2name.size
    loadEngineData.value.id2name.size shouldEqual loadEngineData.value.name2id.size
  }

  "engine data" should "appropriate edges counts" in {
    val data = loadEngineData.value

    data.sources(data.name2id("case1_1")).size shouldEqual 1
    data.sources(data.name2id("case2_1")).size shouldEqual 4
    data.sources(data.name2id("case2_3")).size shouldEqual 0
    data.sources(data.name2id("case3_5")).size shouldEqual 2
  }
}
