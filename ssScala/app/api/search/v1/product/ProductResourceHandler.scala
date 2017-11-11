package api.search.v1.ticket

import javax.inject.{Inject, Provider}

import api.search.v1.product.{
  ProductData,
  ProductRepository,
  ProductRouter,
  ProductsData
}
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

case class ProductsResource(query: String, productsData: ProductsData)

object ProductsResource {
  implicit val productWrites = new Writes[ProductData] {
    override def writes(o: ProductData): JsValue = Json.obj(
      "id" -> o.id.toString,
      "title" -> o.title,
      "idCode" -> o.iaCode
    )
  }

  implicit val productsResourceWrites = new Writes[ProductsResource] {
    override def writes(o: ProductsResource): JsValue = Json.obj(
      "data" -> Json.obj(
        "products" -> o.productsData.products,
        "total" -> o.productsData.total,
      ),
      "nextPage" -> pagePath(o.query, o.productsData.nextPage),
      "previousPage" -> pagePath(o.query, o.productsData.previousPage)
    )
  }

  private def pagePath(query: String, page: Option[Int]): Option[String] =
    page match {
      case Some(p) => Some(s"${ProductRouter.path}/$query?${ProductRouter.pageParam}=$p")
      case None    => None
    }
}

class ProductResourceHandler @Inject()(
    routerProvider: Provider[ProductRouter],
    productRepository: ProductRepository)(implicit ec: ExecutionContext) {

  def find(query: String, page: Option[Int]): Future[ProductsResource] =
    productRepository
      .searchByQuery(query, page)
      .map(productData => ProductsResource(query, productData))

}
