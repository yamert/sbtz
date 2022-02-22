import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.9"

  object Cats {
    lazy val core = "org.typelevel"   %% "cats-core"   % "2.7.0"
    lazy val effect = "org.typelevel" %% "cats-effect" % "3.3.5"
    lazy val all = Seq(core, effect)
  }

  object Logging {
    val logbackVersion = "1.2.10"
    val scalaLoggingVersion = "3.9.4"
    lazy val logback = "ch.qos.logback"                   % "logback-classic" % logbackVersion
    lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging"   % scalaLoggingVersion
    lazy val all = Seq(logback, scalaLogging)
  }
}
