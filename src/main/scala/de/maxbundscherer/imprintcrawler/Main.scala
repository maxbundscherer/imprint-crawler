package de.maxbundscherer.imprintcrawler

object Main extends App {

  import de.maxbundscherer.imprintcrawler.services.WebClientService

  private val webClientService = new WebClientService()

  val targetUrl   = "https://www.example.org/"
  val searchTerms = Vector("Impressum", "Imprint")

  webClientService.printHrefs(targetUrl = targetUrl, searchTerms = searchTerms)
}
