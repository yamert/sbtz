package sbtz.loader

import org.scalatest.EitherValues
import org.scalatest.OptionValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class DataEdgeSpec extends AnyFlatSpec with should.Matchers with EitherValues with OptionValues {

  "pure edges from file" should "be parsed successfully" in {
    val edges = for {
      data <- load
      edges <- DataEdge.parse(data).toEither
    } yield edges

    edges.value.nonEmpty shouldEqual true
    edges.value.size shouldEqual 60
  }

  "contaminated [empty,empty,orig,orig] edges" should "be parsed with 2 errors" in {
    val edges = for {
      data <- load
      edges <- DataEdge.parse(contaminate(data)).toEither
    } yield edges

    val expectedErrorMessage = """Empty 'node id' string
                                 |Empty 'node id' string""".stripMargin

    edges.left.value shouldEqual expectedErrorMessage
  }
}
