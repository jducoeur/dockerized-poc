import Dependencies._

lazy val scalaV = "2.11.12"
lazy val appV = "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    scalaVersion := scalaV,
    version := appV,
    name := "Dockerized PoC",
    // Needed for the in-memory Cassandra driver, used during tests:
    resolvers += "dnvriend".at("http://dl.bintray.com/dnvriend/maven"),
    libraryDependencies ++= Seq(
      scalaTest % Test
    ),
    PlayKeys.playDefaultPort := 9003
  )
  .enablePlugins(JavaAppPackaging, PlayScala, BuildInfoPlugin)
