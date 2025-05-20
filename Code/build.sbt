ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "CODE",
    libraryDependencies ++= Seq(
      // Akka Dependency
      "com.typesafe.akka" %% "akka-actor-typed" % "2.8.8",

      // Logging-Backend für SLF4J (Logback)
      "ch.qos.logback" % "logback-classic" % "1.2.12",

      // SLF4J Bindings (optional, wenn zusätzlich benötigt)
      "org.slf4j" % "slf4j-api" % "1.7.36"
    )
  )