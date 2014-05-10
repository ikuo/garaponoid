import android.Keys._

android.Plugin.androidBuild

name := "Garaponoid"

scalaVersion := "2.10.2"

scalacOptions in Compile ++=
  Seq("-deprecation", "-feature", "-language:implicitConversions", "-unchecked")

platformTarget in Android := "android-16"

// Call install and run without prefix 'android:' {{{
run <<= run in Android

install <<= install in Android
// }}}
