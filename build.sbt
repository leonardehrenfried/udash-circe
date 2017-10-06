enablePlugins(ScalaJSPlugin)

name := "udash-circe"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.3"

val udashVersion = "0.5.0"
val circeVersion = "0.8.0"

libraryDependencies ++= Seq(
  "io.udash" %%% "udash-core-shared" % udashVersion,
  "io.udash" %%% "udash-rest-shared" % udashVersion,
  "io.circe" %%% "circe-core"        % circeVersion,
  "io.circe" %%% "circe-parser"      % circeVersion
)
