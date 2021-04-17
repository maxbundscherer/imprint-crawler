package de.maxbundscherer.imprintcrawler.services

class WebClientService {

  import org.jsoup.Jsoup
  import org.jsoup.nodes.Element

  import scala.util.{ Failure, Success, Try }
  import scala.jdk.CollectionConverters.IteratorHasAsScala

  private case class HrefItem(label: String, target: String) {
    override def toString: String = s"[label=$label][target=$target]"
  }

  private def getHrefs(targetUrl: String): Vector[HrefItem] = {

    val doc = Jsoup.connect(targetUrl).get()

    val elements: Vector[Element] = Vector() ++ doc.select("a").iterator().asScala

    elements.map(e =>
      HrefItem(
        label = e.text,
        target = e.attr("href")
      )
    )
  }

  private def resolveAllImprintHrefs(
      targetUrl: String,
      searchTerms: Vector[String]
  ): Try[Vector[String]] =
    Try {

      val modTargetUrl   = if (targetUrl.endsWith("/")) targetUrl else targetUrl + "/"
      val modSearchTerms = searchTerms.map(_.toLowerCase)

      val trackedImprintUrlsRel: Vector[HrefItem] =
        this
          .getHrefs(modTargetUrl)
          .filter { item =>
            Vector(item.label, item.target)
              .map(_.toLowerCase)
              .map(_.trim)
              .exists(e => modSearchTerms.contains(e))
          } ++ modSearchTerms.map(term => HrefItem(label = s"dummy-$term", target = s"/$term"))

      val trackedImprintUrlsAbs: Vector[String] = trackedImprintUrlsRel.map { t =>
        val url = if (t.target.startsWith("/")) t.target.substring(1) else t.target
        modTargetUrl + url
      }.distinct

      trackedImprintUrlsAbs
    }

  private def getRequest(targetUrl: String): String = {

    val doc = Jsoup.connect(targetUrl)
    doc.get().body().html()
  }

  def printHrefs(targetUrl: String, searchTerms: Vector[String]): Unit = {
    println(s"DEBUG Crawl now $targetUrl")
    this.resolveAllImprintHrefs(targetUrl, searchTerms) match {
      case Failure(exception) => println(s"ERROR (${exception.getLocalizedMessage})")
      case Success(urls) =>
        urls.foreach(d => println(s"INFO Got (${d})"))
    }
  }

}
