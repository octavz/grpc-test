addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.3.4")
addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.34")

val zioGrpcVersion = "0.4.0"

libraryDependencies += "com.thesamet.scalapb.zio-grpc" %% "zio-grpc-codegen" % zioGrpcVersion
