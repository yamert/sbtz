import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.9"

  object Cats {
    lazy val core = "org.typelevel" %% "cats-core" % "2.3.0"
    lazy val effectStd = "org.typelevel" %% "cats-effect-std" % "3.2.9"
    lazy val effect = "org.typelevel" %% "cats-effect" % "3.2.9"
  }

  object Logging {
    val logbackVersion = "1.2.7"
    val scalaLoggingVersion = "3.9.4"
    lazy val logback = "ch.qos.logback" % "logback-classic" % logbackVersion
    lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
    lazy val all = Seq(logback, scalaLogging)
  }
}
