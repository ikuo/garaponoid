import android.Keys._
import android.Dependencies.{apklib,aar}

android.Plugin.androidBuild

name := "Garaponoid"

scalaVersion := "2.10.2"

scalacOptions in Compile ++=
  Seq("-deprecation", "-feature", "-language:implicitConversions", "-unchecked")

platformTarget in Android := "android-16"

resolvers ++= Seq(
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases",
  "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Big Bee Consultants" at "http://repo.bigbeeconsultants.co.uk/repo"
)

libraryDependencies ++= Seq(
  "com.github.ikuo" % "garapon4s_2.10" % "0.2.1-SNAPSHOT",
  "org.scaloid" %% "scaloid" % "3.3-8"
)

libraryDependencies += aar("com.github.gabrielemariotti.cards" % "library" % "1.5.0")

// Call install and run without prefix 'android:' {{{
run <<= run in Android

install <<= install in Android
// }}}
