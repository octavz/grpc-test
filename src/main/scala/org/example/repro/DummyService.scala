package org.example.repro

import zio._
import zio.logging._

import io.grpc.Status
import org.example.repro.greeter_service.ZioGreeterService._
import org.example.repro.models._

trait DummyService extends ZGreeterService[Logging, Any] {
  override def sayHello(request: HelloRequest): ZIO[Logging, Status, HelloReply] =
    ZIO.succeed(HelloReply.of(s"Hello ${request.name}"))
}

object DummyService extends DummyService
