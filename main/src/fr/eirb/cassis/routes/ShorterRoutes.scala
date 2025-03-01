package fr.eirb.cassis.routes

import cask.{Logger, Response, Routes, get}
import castor.Context
import fr.eirb.cassis.Hash
import fr.eirb.cassis.manager.ProtectorManager

private var manager: ProtectorManager = ProtectorManager(Seq.empty, 0)

case class ShorterRoutes()(implicit cc: Context, log: Logger) extends Routes:

  @get("/shrt/:url")
  def welcome(url: String): Response[String] =
    for
      hash <- Hash.option(url)
      protectedURL <- manager.getURL(hash)
    yield renderHtml(html.redirection("https://localhost.ipb.fr:8080/"))

  initialize()
