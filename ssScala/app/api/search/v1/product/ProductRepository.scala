package api.search.v1.product

import javax.inject._

import client.SolrClient
import com.google.inject.ImplementedBy

import scala.concurrent.{ExecutionContext, Future}

final case class ProductData(id: ProductId, title: String, iaCode: String)

final case class ProductsData(products: Seq[ProductData],
                              nextPage: Option[Int],
                              previousPage: Option[Int],
                              total: Int)
final class ProductId private (val underlying: Long) extends AnyVal {
  override def toString: String = underlying.toString
}

object ProductId {
  def apply(raw: Long): ProductId = new ProductId(raw)

  def apply(raw: String): ProductId = {
    require(raw != null)
    new ProductId(raw.toLong)
  }
}

@ImplementedBy(classOf[ProductRepositoryImplWithMockData])
trait ProductRepository {

  def searchByQuery(query: String, page: Option[Int]): Future[ProductsData]

}

@Singleton
class ProductRepositoryImplWithSolr @Inject()(client: SolrClient)(
    implicit ec: ExecutionContext)
    extends ProductRepository {

  //client.XXX
  override def searchByQuery(query: String,
                             page: Option[Int]): Future[ProductsData] = ???
}
@Singleton
class ProductRepositoryImplWithMockData @Inject()()(
    implicit ec: ExecutionContext)
    extends ProductRepository {

  val mockData = List(
    ProductData(ProductId(21392258L), "Cat Sticker", "u-sticker"),
    ProductData(ProductId(15563459L), "Cat Eye Galaxy Sticker", "u-sticker"))

  override def searchByQuery(query: String,
                             page: Option[Int]): Future[ProductsData] =
    page match {
      case Some(p) =>
        Future {
          ProductsData(mockData, Some(p + 1), previousPage(p), mockData.length)
        }
      case None =>
        Future {
          ProductsData(mockData, Some(1), None, mockData.length)
        }
    }

  private def previousPage(page: Int): Option[Int] =
    if (page > 0) Some(page - 1) else None
}
