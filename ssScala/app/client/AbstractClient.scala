package client

import play.api.libs.ws.WSResponse
import scala.concurrent.Future
import scala.concurrent.duration._

trait AbstractClient {

  def prefix: String

  def headers: Seq[(String, String)]

  def timeOut: Duration = 10000.millis

  def get(path: String,
          parameters: Seq[(String, String)] = Seq.empty): Future[WSResponse]

  def buildUrl(path: String): String = {
    import com.netaporter.uri.dsl._
    val url = prefix / path
    url.toString()
  }
}
