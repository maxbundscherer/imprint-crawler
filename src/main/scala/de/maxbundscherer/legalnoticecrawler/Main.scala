package de.maxbundscherer.legalnoticecrawler

object Main extends App {

  import de.maxbundscherer.legalnoticecrawler.services.WebClientService

  private val webClientService = new WebClientService()

  val targetUrl   = "https://www.example.org/"
  val searchTerms = Vector("Impressum", "Imprint", "Legal")

  webClientService.printHrefs(targetUrl = targetUrl, searchTerms = searchTerms)
}
