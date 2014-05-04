// required SBT components
import sbt._
import Keys._
import Defaults._

// SBT-Android keys and helper methods
import sbtandroid.AndroidPlugin._

object AndroidBuild extends Build {

  val globalSettings = Seq (
    name := "Garaponoid",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.10.3",
    platformName := "android-15",
    useProguard := true,
    proguardOptions += "@project/proguard.cfg",
    keyalias := "change-me",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest"     % "2.0" % "test",
      "org.slf4j"      % "slf4j-android" % "1.7.6"
    ),
    scalacOptions in Compile ++= Seq(
      "-deprecation","-feature","-language:implicitConversions","-unchecked"
    ),
    javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.6", "-target", "1.6"),
    javaOptions in Compile += "-Dscalac.patmat.analysisBudget=off",
    initialize ~= { _ ⇒
      sys.props("scalac.patmat.analysisBudget") = "819200"
    }
  )

  lazy val main = AndroidProject (
    id       = "Garaponoid",
    base     = file("."),
    settings = globalSettings
  )

  lazy val tests = AndroidTestProject (
    id       = "tests",
    base     = file("tests"),
    settings = globalSettings
  ).
  dependsOn(main % "provided").
  settings(name := "GaraponoidTests")
}
