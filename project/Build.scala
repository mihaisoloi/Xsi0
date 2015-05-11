import sbt._
import sbt.Keys._
import play.Play.autoImport._

object Build extends Build {
  val commonSettings = Seq(
    organization := "livongo",

    scalaVersion := "2.11.6",
    scalacOptions ++= Seq(
      "-unchecked", "-deprecation", "-feature", "-Xlint", "-target:jvm-1.6",
      "-Yinline-warnings", "-Ywarn-adapted-args",
      "-Ywarn-dead-code", "-Ywarn-inaccessible", "-Ywarn-nullary-override",
      "-Ywarn-nullary-unit", "-Xlog-free-terms"
    ),

    resolvers ++= Seq(
      "JBoss repository" at "https://repository.jboss.org/nexus/content/repositories/",
      "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots",
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
      "Apache Snapshots" at "https://repository.apache.org/content/repositories/snapshots/",
      "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
    ),

    libraryDependencies ++= Seq(
      ws // web-services, from Play
    )

  )

  val main = Project(id = "Xsi0", base = file("."))
    .enablePlugins(play.PlayScala)
    .settings(commonSettings : _*)
}