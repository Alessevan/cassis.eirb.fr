package fr.eirb.cassis.routes

import cask.{Logger, Response, Routes, get}
import castor.Context
import fr.eirb.cassis.renderer.*
import fr.eirb.cassis.renderer.{body, html, rendererToResponse, script}

case class ShorterRoutes()(implicit cc: Context, log: Logger) extends Routes:

  @get("/shrt/:url")
  def welcome(url: String): Response[String] =
    rendererToResponse(html(body = body(script("alert(1);"))))

  initialize()
