package fr.eirb.cassis.routes

import cask.{Logger, Response, Routes, get}
import castor.Context

case class ShorterRoutes()(implicit cc: Context, log: Logger) extends Routes:

  @get("/shrt/:url")
  def welcome(url: String): Response[String] =
    Response(html.redirection("https://localhost.ipb.fr:8080/").toString(), headers = Seq("Content-type" -> "text/html"))

  initialize()
