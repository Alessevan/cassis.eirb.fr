package fr.eirb.cassis.routes

import cask.{Logger, Response, Routes, get}
import castor.Context

case class WelcomeRoutes()(implicit cc: Context, log: Logger) extends Routes:

  @get("/")
  def welcome(): Response[String] =
    Response(html.home().toString(), headers = Seq("Content-type" -> "text/html"))

  initialize()
