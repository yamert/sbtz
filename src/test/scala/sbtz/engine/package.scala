package sbtz

import sbtz.loader._

package object engine {

  def loadEngine: Engine = (for {
    data <- loadEngineData
  } yield Engine(data)).toOption.get
}
