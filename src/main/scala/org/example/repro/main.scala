package org.example.repro

import zio._
import zio.logging._
import zio.logging.slf4j._
import io.grpc.protobuf.services.ProtoReflectionService
import io.grpc.ServerBuilder
import scalapb.zio_grpc.{ManagedServer, Server, ServiceList}

object main extends zio.App {
  val liveLogger: ZLayer[Any, Nothing, Logging] =
    Slf4jLogger.make { (_, message) => message }

  def welcome: ZIO[ZEnv with Logging, Throwable, Unit] =
    log.info("Server is running. Press Ctrl-C to stop.")

  def services: ServiceList[ZEnv with Logging] =
    ServiceList.add(DummyService)

  def builder(port: Int): ServerBuilder[_] =
    ServerBuilder.forPort(port).addService(ProtoReflectionService.newInstance())

  def server: ZManaged[ZEnv with Logging, Throwable, Server.Service] =
    for {
      res <- ManagedServer.fromServiceList(builder(8080), services)
    } yield res

  val appLogic = welcome *> server.useForever

  def run(args: List[String]) = {
    val layer: ZLayer[Any, Throwable, Logging] = liveLogger

    appLogic.provideCustomLayer(layer).exitCode
  }

}
