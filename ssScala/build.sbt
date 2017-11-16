name := """ss"""
organization := "com.redbubble"

version := "0.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += "net.logstash.logback" % "logstash-logback-encoder" % "4.9"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "com.netaporter" %% "scala-uri" % "0.4.16"
libraryDependencies += "org.apache.solr" % "solr-solrj" % "6.6.0"
