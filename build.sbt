resolvers ++= Seq(
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases",
  "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Big Bee Consultants" at "http://repo.bigbeeconsultants.co.uk/repo"
)

libraryDependencies ++= Seq(
  "com.github.ikuo" % "garapon4s_2.10" % "0.2.1-SNAPSHOT",
  "org.scaloid" %% "scaloid" % "3.3-8"
)

libraryDependencies += aarlib("com.github.gabrielemariotti.cards" % "library" % "1.5.0")
