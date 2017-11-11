package client

import javax.inject.Inject

import play.api.libs.ws.{WSClient, WSResponse}
import scala.concurrent.Future

class SolrClient @Inject()(client: WSClient) extends AbstractClient {

  override def prefix: String = ???

  override def headers: Seq[(String, String)] = List(
    "Accept" -> "application/json",
    "Content-Type" -> "application/json"
  )

  def get(path: String,
          parameters: Seq[(String, String)] = Seq.empty): Future[WSResponse] =
    ???
}
