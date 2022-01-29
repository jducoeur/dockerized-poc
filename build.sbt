import Dependencies._

lazy val scalaV = "2.11.12"
lazy val appV = "0.1.0-SNAPSHOT"
// This appears to be the last 2.4 release:
lazy val akkaV = "2.4.20"

lazy val root = (project in file("."))
  .settings(
    scalaVersion := scalaV,
    version := appV,
    name := "Dockerized PoC",
    // Needed for the in-memory Cassandra driver, used during tests:
    resolvers += "dnvriend".at("http://dl.bintray.com/dnvriend/maven"),
    libraryDependencies ++= Seq(
      scalaTest % Test,
      // NOTE: this is a newer version than Querki has, despite being pretty old. The problem with it is that
      // it pulls in a version of Netty that conflicts with Play 2.5, which is why we switch to akka-http
      // below. We need this newer SDK so that we have access to the SecretsManager:
      "com.amazonaws" % "aws-java-sdk" % "1.12.99",
      "com.typesafe.akka" %% "akka-remote" % akkaV,
      "com.typesafe.akka" %% "akka-cluster" % akkaV,
      "com.typesafe.akka" %% "akka-cluster-sharding" % akkaV,
      "com.typesafe.akka" %% "akka-distributed-data-experimental" % akkaV
    )
  )
  // NOTE: we need to turn on akka-http and turn off Netty, because the version of Netty built into
  // Play 2.5 conflicts with the version in the AWS SDK:
  .enablePlugins(JavaAppPackaging, PlayScala, BuildInfoPlugin, PlayAkkaHttpServer)
  .disablePlugins(PlayNettyServer)
