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

scalafmtVersion in ThisBuild := "1.3.0"

// defaults
releaseTagComment := s"Release ${(version in ThisBuild).value}"
releaseCommitMessage := s"Set version to ${(version in ThisBuild).value}"

javaOptions ~= (_.filter(_.startsWith("-D")))

sonatypeGithost in ThisBuild := (fommil.SonatypeKeys.Github, "leonardehrenfried", "udash-circe")

licenses in ThisBuild := Seq(Apache2)

fork := false

val udashVersion = "0.6.0-M7"
val circeVersion = "0.8.0"

lazy val commonSettings =
  Seq(
    organization := "io.leonard",
    scalaVersion := "2.12.4",
    fork := false,
    licenses := Seq(Apache2),
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
