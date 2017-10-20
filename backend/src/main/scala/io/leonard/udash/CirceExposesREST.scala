package io.leonard.udash

import io.udash.rest.server.ExposesREST

class UdashExposesREST[ServerRPCType: CirceRESTFramework.ValidServerREST: CirceRESTFramework.RPCMetadata](
    localRest: ServerRPCType)(
    implicit protected val localRpcAsRaw: CirceRESTFramework.AsRawRPC[ServerRPCType]
) extends ExposesREST[ServerRPCType](localRest)
    with RESTConverters {
  override val framework = CirceRESTFramework

  protected val rpcMetadata: CirceRESTFramework.RPCMetadata[ServerRPCType] =
    implicitly[CirceRESTFramework.RPCMetadata[ServerRPCType]]

}
