resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

addSbtPlugin("com.hanhuy.sbt" % "android-sdk-plugin" % "1.2.17")
