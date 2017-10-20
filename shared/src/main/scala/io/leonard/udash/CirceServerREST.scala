package io.leonard.udash

import io.udash.rest.{DefaultRESTConnector, UdashRESTFramework}
import io.udash.rest.internal.{RESTConnector, UsesREST}

import scala.concurrent.ExecutionContext

trait RESTConverters {
  val framework: UdashRESTFramework

  def rawToHeaderArgument(raw: framework.RawValue): String =
    stripQuotes(framework.rawToString(raw))
  def rawToQueryArgument(raw: framework.RawValue): String =
    stripQuotes(framework.rawToString(raw))
  def rawToURLPart(raw: framework.RawValue): String =
    stripQuotes(framework.rawToString(raw))

  private def stripQuotes(s: String): String =
    s.stripPrefix("\"").stripSuffix("\"")
}

/** Default REST usage mechanism using [[io.leonard.udash.CirceServerREST]]. */
class CirceServerREST[
    ServerRPCType: CirceRESTFramework.AsRealRPC: CirceRESTFramework.RPCMetadata: CirceRESTFramework.ValidREST](
    override protected val connector: RESTConnector)(implicit ec: ExecutionContext)
    extends UsesREST[ServerRPCType]
    with RESTConverters {

  override val framework = CirceRESTFramework

  override val remoteRpcAsReal: CirceRESTFramework.AsRealRPC[ServerRPCType] =
    implicitly[framework.AsRealRPC[ServerRPCType]]

  override val rpcMetadata =
    implicitly[framework.RPCMetadata[ServerRPCType]]

}

object CirceServerREST {

  /** Creates [[io.udash.rest.DefaultServerREST]] with [[io.udash.rest.DefaultRESTConnector]] for provided REST interfaces. */
  def apply[ServerRPCType: CirceRESTFramework.AsRealRPC: CirceRESTFramework.RPCMetadata: CirceRESTFramework.ValidREST](
      host: String,
      port: Int,
      pathPrefix: String = "")(implicit ec: ExecutionContext): ServerRPCType = {
    val serverConnector = new DefaultRESTConnector(host, port, pathPrefix)
    val serverRPC: CirceServerREST[ServerRPCType] =
      new CirceServerREST[ServerRPCType](serverConnector)
    serverRPC.remoteRpc
  }
}
