name := "akka-tutorials"

version := "0.1"

organization := "com.danielasfregola"

scalaVersion := "2.11.5"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/")

libraryDependencies ++= {
  val AkkaVersion       = "2.4.2"
  Seq(
    "com.typesafe.akka" %% "akka-http-experimental" % AkkaVersion,
    "com.typesafe.akka" %% "akka-cluster-tools" % AkkaVersion
  )
}
