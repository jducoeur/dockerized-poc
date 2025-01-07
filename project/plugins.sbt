// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
// resolvers += "Typesafe repository".at("http://repo.typesafe.com/typesafe/releases/")

// Needed for ConductR, according to https://github.com/typesafehub/conductr-lib
//resolvers += bintrayRepo("typesafe", "maven-releases")

// Needed for Li Haoyi's stuff
resolvers += "bintray/non".at("http://dl.bintray.com/non/maven")

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.19")

// Docker plugin: https://github.com/marcus-drake/sbt-docker
// addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.0.0")

// NOTE FOR QUERKI: these plugins are antique, and are just for optimization. Remove for now, and seek
// alternatives. Note that these are enabled via SbtWeb, which isn't *quite* as outdated.
//addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.1")
//addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.0")
//addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.0")

// Adds the stats command -- see https://github.com/orrsella/sbt-stats
// NOTE FOR QUERKI: this is very old and not well-maintained. Do we care?
//addSbtPlugin("com.orrsella" % "sbt-stats" % "1.0.7")

// For the JVM side of the shared code:
// NOTE FOR QUERKI: do we actually need this? It is decently-maintained, so we should update it if so:
//addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.0.4")

// So that the Play application can access the version and build date:
// NOTE FOR QUERKI: this is fairly out of date, but anything newer causes sbt to break. Needs more investigation,
// but at least in theory isn't important.
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")
