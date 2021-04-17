package de.maxbundscherer.imprintcrawler.services

class WebClientService {

  import sttp.client3._

  private val backend = HttpURLConnectionBackend()

  private def getRequest(targetUrl: String): Either[String, String] =
    basicRequest
      .get(uri"$targetUrl")
      .send(backend)
      .body

  private def getHrefs(requestBody: String): Either[String, Vector[String]] = Left("not impl.")

  def printHrefs(targetUrl: String): Unit =
    this.getRequest(targetUrl) match {
      case Left(err) => println(s"Got network error ($err)")
      case Right(requestBody) =>
        this.getHrefs(requestBody) match {
          case Left(err)   => println(s"Got parse error ($err)")
          case Right(data) => println(s"Got href data ($data)")
        }

    }

}
