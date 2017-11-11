package api.search.v1.ticket

import javax.inject.Inject

import api.search.v1.product.{
  ProductBaseController,
  ProductControllerComponents
}
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

class ProductController @Inject()(cc: ProductControllerComponents)(
    implicit ec: ExecutionContext)
    extends ProductBaseController(cc) {

  def search(query: String, page: Option[Int]): Action[AnyContent] =
    ProductAction.async { implicit request =>
      productResourceHandler.find(query, page).map { products =>
        Ok(Json.toJson(products))
      }
    }
}
