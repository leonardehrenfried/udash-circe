package io.leonard.udash

import io.udash.rest.UdashRESTFramework
import io.udash.rest.server.ExposesREST

class CirceExposesREST[ServerPRCType: UdashRESTFramework#AsRawRPC] extends ExposesREST {

  override val framework = CirceRESTFramework

  override protected def localRpcAsRaw = ???

  override protected val rpcMetadata = CirceRESTFramework.RPCMetadata

  override def headerArgumentToRaw(raw: String, isStringArg: Boolean) = ???

  override def queryArgumentToRaw(raw: String, isStringArg: Boolean) = ???

  override def urlPartToRaw(raw: String, isStringArg: Boolean) = ???
}

