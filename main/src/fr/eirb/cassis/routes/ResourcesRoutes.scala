package fr.eirb.cassis.routes

import cask.decorators.compress
import cask.endpoints.{staticFiles, staticResources}
import cask.{Logger, Redirect, Response, Routes, get}
import castor.Context

class ResourcesRoutes()(implicit cc: Context, log: Logger) extends Routes:

  @compress
  @staticFiles("/static/:file")
  def static(file: String): String =
    print("hey")
    file

  @get("favicon.ico")
  def icon(): Response[String] =
    print("oh")
    Redirect("static/favicon.ico")

  initialize()
