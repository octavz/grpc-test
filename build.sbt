scalaVersion := "2.13.3"
name := "repro"
organization := "org.example"
version := "0.0.1"

val zioVersion = "1.0.1"
val logbackClassicVersion = "1.2.3"
val zioLoggingVersion = "0.5.0"
val grpcVersion = "1.31.1"

Global / onChangedBuildSource := ReloadOnSourceChanges

libraryDependencies ++= Seq(
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
  "io.grpc" % "grpc-netty" % grpcVersion,
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-logging-slf4j" % zioLoggingVersion,
  "ch.qos.logback" % "logback-classic" % logbackClassicVersion
)

configs(IntegrationTest)

PB.targets in Compile := Seq(
  scalapb.gen(grpc = true) -> (sourceManaged in Compile).value,
  scalapb.zio_grpc.ZioCodeGenerator -> (sourceManaged in Compile).value
)

scalacOptions ++= Seq(
  "-encoding",
  "utf-8", //Specify character encoding used by source files.
  "-explaintypes", //Explain type errors in more detail
  "-feature", //Emit warning and location for usage of features that should be imported explicitly.
  "-Xlint:missing-interpolator", //A string literal appears to be missing an interpolator id.
  "-Xlint:inaccessible", //Warn about inaccessible types in method signatures.
  "-language:implicitConversions", //Allow definition of implicit functions called views
  "-Ymacro-annotations",
  "-Wunused",
  "-Xfatal-warnings"
)
