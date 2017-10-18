enablePlugins(ScalaJSPlugin)

val udashVersion = "0.6.0-M6"
val circeVersion = "0.8.0"

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
releaseTagComment := s"Release ${(version in ThisBuild).value}"
releaseCommitMessage := s"Set version to ${(version in ThisBuild).value}"

javaOptions ~= (_.filter(_.startsWith("-D")))

sonatypeGithost in ThisBuild := ("github.com", "leonardehrenfried", "udash-circe")

licenses in ThisBuild := Seq(Apache2)

fork := false

lazy val commonSettings =
  Seq(
    organization := "io.leonard",
    name := "udash-circe",
    scalaVersion := "2.12.3",
    fork := false,
    licenses in ThisBuild := Seq(Apache2),
    // scalaVersion from .travis.yml via sbt-travisci
    // scalaVersion := "2.12.3",
    unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value),
    libraryDependencies ++= Seq(
      "io.udash" %%% "udash-core-shared" % udashVersion,
      "io.udash" %%% "udash-rest-shared" % udashVersion,
      "io.circe" %%% "circe-core"        % circeVersion,
      "io.circe" %%% "circe-parser"      % circeVersion
    )
  )

lazy val `udash-circe-frontend` = project
  .in(file("frontend"))
  .settings(commonSettings)

lazy val `udash-circe` =
  project
    .in(file("."))
    .aggregate(
      `udash-circe-frontend`
    )
    .settings(
      unmanagedSourceDirectories.in(Compile) := Seq.empty,
      unmanagedSourceDirectories.in(Test) := Seq.empty,
      publishArtifact := false
    )
    .settings(commonSettings)
