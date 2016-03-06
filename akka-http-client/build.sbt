name := "akka-tutorials"

version := "0.1"

organization := "com.danielasfregola"

scalaVersion := "2.11.5"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
                  Resolver.bintrayRepo("hseeberger", "maven"))

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.2",
  "de.heikoseeberger" %% "akka-http-json4s" % "1.5.2",
  "org.json4s"        %% "json4s-native"   % "3.2.11"
)

