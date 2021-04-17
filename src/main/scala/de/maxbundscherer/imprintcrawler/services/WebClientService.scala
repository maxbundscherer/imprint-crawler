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
    val filtered: Vector[HrefItem] =
      this
        .getHrefs(targetUrl)
        .filter { item =>
          val filterStrings: Vector[String] =
            Vector("Impressum", "Imprint")
              .map(_.toLowerCase())
              .map(_.trim)

          val targets: Vector[String] =
            Vector(item.label, item.target)
              .map(_.toLowerCase)
              .map(_.trim)

          targets.exists(e => filterStrings.contains(e))
        }

    println(s"Found imprint urls (ignored) ($filtered)")

  }

}
