lazy val scalaGuice = (project in file(".")).
  settings(
    name := "scala-guice",
    version := "0.0.0-SNAPSHOT",
    scalaVersion := "2.11.8"
  )

libraryDependencies += "com.google.inject" % "guice" % "4.1.0"
