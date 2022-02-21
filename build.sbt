import Dependencies._

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / javacOptions    ++= Seq("-source", "17", "-target", "17", "-Xlint")
ThisBuild / scalacOptions   ++= Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8", "-target:jvm-17")
ThisBuild / version         := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.sbtz"
ThisBuild / organizationName := "example"
ThisBuild / fork            := true

lazy val root = (project in file("."))
  .settings(
    name := "sbtz",
    libraryDependencies += scalaTest % Test
  )
