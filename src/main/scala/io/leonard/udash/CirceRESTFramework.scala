package io.leonard.udash

import io.circe._
import io.udash.rest.UdashRESTFramework
import io.udash.rpc.DefaultUdashSerialization

trait CirceRESTFramework extends UdashRESTFramework {
  override type RawValue  = Json
  override type Reader[T] = Decoder[T]
  override type Writer[T] = Encoder[T]

  override def stringToRaw(string: String) =
    parser.parse(string).getOrElse(throw new RuntimeException(s"Could not parse JSON $string"))

  override def rawToString(raw: Json) = Printer.noSpaces.pretty(raw)

  override def read[T](raw: Json)(implicit decoder: Decoder[T]) =
    raw.as[T] match {
      case Left(failure) =>
        throw new RuntimeException(s"Could not decode JSON: $failure")
      case Right(value) => value
    }

  override def write[T](value: T)(implicit encoder: Encoder[T]) =
    encoder.apply(value)
}

object CirceRESTFramework extends UdashRESTFramework with DefaultUdashSerialization with CirceRESTFramework
