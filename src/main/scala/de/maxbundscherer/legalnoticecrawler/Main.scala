package de.maxbundscherer.legalnoticecrawler

object Main extends App {

  import de.maxbundscherer.legalnoticecrawler.services.WebClientService

  private val webClientService = new WebClientService()

  //TODO: Add target url resolver
  //TODO: Add legal-notice term (-)

  val targetUrl   = "https://www.example.org/"
  val searchTerms = Vector("Impressum", "Legal", "Imprint")

  webClientService.printHrefs(targetUrl = targetUrl, searchTerms = searchTerms)
}
