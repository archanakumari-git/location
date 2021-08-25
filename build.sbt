name := "location"

version := "0.1"

lazy val akkaHttpVersion = "10.2.6"
lazy val akkaVersion    = "2.6.16"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.lightbend.location",
      scalaVersion    := "2.13.4"
    )),
    name := "location",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion
    )
  )