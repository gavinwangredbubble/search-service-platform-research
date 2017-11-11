import play.api.http.HttpErrorHandler
import play.api.http.Status._
import play.api.mvc.Results._
import play.api.mvc._
import scala.concurrent._

class ErrorHandler extends HttpErrorHandler {

  private val logger =
    org.slf4j.LoggerFactory.getLogger("application.ErrorHandler")

  override def onClientError(request: RequestHeader,
                             statusCode: Int,
                             message: String): Future[Result] = {
    logger.debug(
      s"onClientError: statusCode = $statusCode, uri = ${request.uri}, message = $message")

    Future.successful {
      val result = statusCode match {
        case BAD_REQUEST =>
          Results.BadRequest(message)
        case FORBIDDEN =>
          Results.Forbidden(message)
        case NOT_FOUND =>
          Results.NotFound(message)
        case clientError if statusCode >= 400 && statusCode < 500 =>
          Results.Status(statusCode)
        case nonClientError =>
          val msg =
            s"onClientError invoked with non client error status code $statusCode: $message"
          throw new IllegalArgumentException(msg)
      }
      result
    }
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    logger.debug(s"onServerError: request = $request, exception = ${exception}")

    Future.successful(InternalServerError(s"A server error occurred."))
  }
}
