package de.maxbundscherer.imprintcrawler

object Main extends App {

  import de.maxbundscherer.imprintcrawler.services.WebClientService

  private val webClientService = new WebClientService()

  webClientService.printHrefs(targetUrl = "http://example.org/")
}
