package io.leonard.udash

import io.udash.rest.server.ExposesREST
import io.udash.rpc.serialization.URLEncoder

class UdashExposesREST[ServerRPCType: CirceRESTFramework.ValidServerREST: CirceRESTFramework.RPCMetadata](
    localRest: ServerRPCType)(
    implicit protected val localRpcAsRaw: CirceRESTFramework.AsRawRPC[ServerRPCType]
) extends ExposesREST[ServerRPCType](localRest) {
  override val framework = CirceRESTFramework

  protected val rpcMetadata: CirceRESTFramework.RPCMetadata[ServerRPCType] =
    implicitly[CirceRESTFramework.RPCMetadata[ServerRPCType]]

  override def headerArgumentToRaw(raw: String, isStringArg: Boolean): framework.RawValue = rawArg(raw, isStringArg)
  override def queryArgumentToRaw(raw: String, isStringArg: Boolean): framework.RawValue  = rawArg(raw, isStringArg)
  override def urlPartToRaw(raw: String, isStringArg: Boolean): framework.RawValue =
    rawArg(URLEncoder.decode(raw), isStringArg)

  private def rawArg(raw: String, isStringArg: Boolean): framework.RawValue =
    if (isStringArg) framework.stringToRaw(s""""$raw"""")
    else framework.stringToRaw(raw)
}
