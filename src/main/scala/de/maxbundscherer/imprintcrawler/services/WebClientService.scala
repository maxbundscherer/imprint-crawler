package de.maxbundscherer.imprintcrawler.services

class WebClientService {

  import org.jsoup.Jsoup
  import org.jsoup.nodes.Element

  import scala.jdk.CollectionConverters.IteratorHasAsScala

  private case class HrefItem(label: String, target: String)

  private def getHrefs(targetUrl: String): Vector[HrefItem] = {

    val doc = Jsoup.connect(targetUrl).get()

    val elements: Vector[Element] = Vector() ++ doc.select("a").iterator().asScala

    elements.map(e =>
      HrefItem(
        label = e.attr("href"),
        target = e.text
      )
    )
  }

  def printHrefs(targetUrl: String): Unit = {

    val hrefs = this.getHrefs(targetUrl)

    hrefs.foreach(d => println(d))

  }

}
