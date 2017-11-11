package api.search.v1.product

import javax.inject.Inject

import api.search.v1.ticket.ProductController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._
import settings.RouteSettings

class ProductRouter @Inject()(controller: ProductController)
    extends SimpleRouter {

  override def routes: Routes = {
    case GET(p"/$query" ? q_?"page=${int(page)}") =>
      controller.search(query, page)
  }
}

object ProductRouter {
  val pageParam = "page"
  val path = s"${RouteSettings.prefix}/products"
}
