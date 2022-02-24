package sbtz

import sbtz.loader._

package object engine {

  def loadEngine: Engine = (for {
    data <- loadEngineData
  } yield Engine(data)).toOption.get

  val c1_1 = "case1_1"
  val c1_99 = "case1_99"
  val c2_3 = "case2_3"
  val (c3_1, c3_2, c3_3, c3_4, c3_7, c3_8) = ("case3_1", "case3_2", "case3_3", "case3_4", "case3_7", "case3_8")
  val (c99_1, c99_2, c99_3) = ("case99_1", "case99_2", "case99_3")
}
