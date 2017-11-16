package client

import javax.inject.Inject

import org.apache.solr.client.solrj.{SolrClient, SolrQuery}
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.apache.solr.client.solrj.response.QueryResponse
import org.apache.solr.common.SolrDocumentList
import play.api.Logger
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import org.apache.solr.client.solrj.SolrClient

import org.apache.solr.client.solrj.impl.CloudSolrClient
import collection.JavaConverters._


class SolrClient @Inject()(client: WSClient) extends AbstractClient {

  val URL = "http://localhost:8080/solr/collection1"
  val solr = new HttpSolrClient.Builder(URL).build();

  val cloudSolr = new CloudSolrClient.Builder().withZkHost(URL).build

  override def prefix: String = ???

  override def headers: Seq[(String, String)] = List(
    "Accept" -> "application/json",
    "Content-Type" -> "application/json"
  )

  def get(path: String,
          parameters: Seq[(String, String)] = Seq.empty): Future[WSResponse] = ???

  def getSolrResult(path: String,
                    parameters: Seq[(String, String)] = Seq.empty): Future[SolrDocumentList] = {

    val query = new SolrQuery()
    query.set("q", "cat")
    query.setMoreLikeThisFields("title_text", "tags_text")

    Logger.debug(s"$query")

    val result = solr.query(query).getResults
    Logger.debug(s"$result")

    Future {
      result
    }
  }

}
