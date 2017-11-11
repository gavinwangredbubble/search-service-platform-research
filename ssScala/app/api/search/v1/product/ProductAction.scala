package api.search.v1.product

import javax.inject.Inject

import api.search.v1.ticket.ProductResourceHandler
import net.logstash.logback.marker.LogstashMarker
import play.api.{Logger, MarkerContext}
import play.api.http.{FileMimeTypes, HttpVerbs}
import play.api.i18n.{Langs, MessagesApi}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions

class ProductRequest[A](request: Request[A]) extends WrappedRequest(request)

trait RequestMarkerContext {
  import net.logstash.logback.marker.Markers

  private def marker(tuple: (String, Any)) = Markers.append(tuple._1, tuple._2)

  private implicit class RichLogstashMarker(marker1: LogstashMarker) {
    def &&(marker2: LogstashMarker): LogstashMarker = marker1.and(marker2)
  }

  implicit def requestHeaderToMarkerContext(
      implicit request: RequestHeader): MarkerContext = {
    MarkerContext {
      marker("id" -> request.id) && marker("host" -> request.host) && marker(
        "remoteAddress" -> request.remoteAddress)
    }
  }

}

class ProductActionBuilder @Inject()(playBodyParsers: PlayBodyParsers)(
    implicit val executionContext: ExecutionContext)
    extends ActionBuilder[ProductRequest, AnyContent]
    with RequestMarkerContext
    with HttpVerbs {

  val parser: BodyParser[AnyContent] = playBodyParsers.anyContent

  type ProductRequestBlock[A] = ProductRequest[A] => Future[Result]

  private val logger = Logger(this.getClass)

  override def invokeBlock[A](request: Request[A],
                              block: ProductRequestBlock[A]): Future[Result] = {
    // Convert to marker context and use request in block
    implicit val markerContext: MarkerContext = requestHeaderToMarkerContext(
      request)
    logger.trace(s"invokeBlock: ")

    val future = block(new ProductRequest(request))

    future.map { result =>
      request.method match {
        case GET | HEAD =>
          result.withHeaders("Cache-Control" -> s"max-age: 100")
        case other =>
          result
      }
    }
  }
}

case class ProductControllerComponents @Inject()(
    productActionBuilder: ProductActionBuilder,
    productResourceHandler: ProductResourceHandler,
    actionBuilder: DefaultActionBuilder,
    parsers: PlayBodyParsers,
    messagesApi: MessagesApi,
    langs: Langs,
    fileMimeTypes: FileMimeTypes,
    executionContext: scala.concurrent.ExecutionContext)
    extends ControllerComponents

class ProductBaseController @Inject()(pcc: ProductControllerComponents)
    extends BaseController
    with RequestMarkerContext {
  override protected def controllerComponents: ControllerComponents = pcc

  def ProductAction: ProductActionBuilder = pcc.productActionBuilder

  def productResourceHandler: ProductResourceHandler =
    pcc.productResourceHandler
}
