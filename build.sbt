enablePlugins(ScalaJSPlugin)

organization := "io.leonard"

name := "udash-circe"

scalaVersion := "2.12.3"

fork := false

sonatypeGithost := ("github", "leonardehrenfried", "udash-circe")

licenses := Seq(Apache2)

val udashVersion = "0.5.0"
val circeVersion = "0.8.0"

libraryDependencies ++= Seq(
  "io.udash" %%% "udash-core-shared" % udashVersion,
  "io.udash" %%% "udash-rest-shared" % udashVersion,
  "io.circe" %%% "circe-core"        % circeVersion,
  "io.circe" %%% "circe-parser"      % circeVersion
)

import ReleaseTransformations._

releasePublishArtifactsAction := PgpKeys.publishSigned.value // Use publishSigned in publishArtifacts step

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)

// defaults
releaseTagComment    := s"Release ${(version in ThisBuild).value}"
releaseCommitMessage := s"Set version to ${(version in ThisBuild).value}"

javaOptions ~= (_.filter(_.startsWith("-D")))
