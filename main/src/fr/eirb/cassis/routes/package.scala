package fr.eirb.cassis.routes

import cask.{Cookie, Response}

def renderHtml(appendable: play.twirl.api.HtmlFormat.Appendable, cookies: Seq[Cookie] = Seq.empty): Response[String] =
  Response(appendable.toString(), statusCode = 200, headers = Seq("Content-type" -> "text/html; charset=utf-8"), cookies = cookies)
