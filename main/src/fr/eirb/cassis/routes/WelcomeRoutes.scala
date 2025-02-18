package fr.eirb.cassis.routes

import cask.{Logger, Response, Routes, get}
import castor.Context
import fr.alessevan.annales.renderer.*
import fr.eirb.cassis.renderer.{body, html, rendererToResponse, script}

case class WelcomeRoutes()(implicit cc: Context, log: Logger) extends Routes:

  @get("/")
  def welcome(): Response[String] =
    rendererToResponse(html(body = body(script("alert(1);"))))

  initialize()
