package sbtz.loader

import org.scalatest.EitherValues
import org.scalatest.OptionValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class LoadedDataSpec extends AnyFlatSpec with should.Matchers with EitherValues with OptionValues {

  "reader" should "read file with header" in {
    val loaded = LoadedData.load("case_test.csv", cutHeader = true)

    loaded.value.lines.nonEmpty shouldEqual true
    loaded.value.lines.size shouldEqual 60

    loaded.value.header.value shouldEqual "source,target,type,cases"
    loaded.value.lines(1) shouldEqual "case2_1,case2_1,501,case2"
  }

  "reader" should "read file without header" in {
    val loaded = LoadedData.load("case_test.csv", cutHeader = false)

    loaded.value.lines.nonEmpty shouldEqual true
    loaded.value.lines.size shouldEqual 61

    loaded.value.header.isEmpty shouldEqual true
  }
}
