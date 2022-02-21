package example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class MySpec extends AnyFlatSpec with should.Matchers {

  "" should "" in {
    true
  }
}
