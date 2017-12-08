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

javaOptions in ThisBuild := Seq()

fork := false

// POM settings for Sonatype
homepage := Some(url("https://github.com/leonardehrenfried/udash-circe"))
scmInfo := Some(
  ScmInfo(url("https://github.com/leonardehrenfried/udash-circe"), "git@github.com:leonardehrenfried/udash-circe.git"))

developers := List(
  Developer("leonardehrenfried", "Leonard Ehrenfried", "mail@leonard.io", url("https://github.com/leonardehrenfried")))
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
publishMavenStyle := true

// Add sonatype repository settings
publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

val udashVersion = "0.6.0-M11"
val circeVersion = "0.8.0"

lazy val commonSettings =
  Seq(
    organization := "io.leonard",
    scalaVersion := "2.12.4",
    fork := false,
    unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value)
  )

lazy val `udash-circe-shared` = project
  .in(file("shared"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "udash-circe-shared",
    libraryDependencies ++= Seq(
      "io.udash" %%% "udash-rest-shared" % udashVersion,
      "io.circe" %%% "circe-parser"      % circeVersion
    )
  )
  .settings(commonSettings)

lazy val `udash-circe-backend` = project
  .in(file("backend"))
  .settings(
    name := "udash-circe-backend",
    libraryDependencies ++= Seq(
      "io.udash" %%% "udash-rest-backend" % udashVersion,
      "io.circe" %%% "circe-parser"       % circeVersion
    )
  )
  .settings(commonSettings)
  .dependsOn(`udash-circe-shared`)

lazy val `udash-circe` =
  project
    .in(file("."))
    .aggregate(
      `udash-circe-shared`,
      `udash-circe-backend`
    )
    .settings(
      unmanagedSourceDirectories.in(Compile) := Seq.empty,
      unmanagedSourceDirectories.in(Test) := Seq.empty,
      publishArtifact := false
    )
    .settings(commonSettings)
