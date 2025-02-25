package fr.eirb.cassis

import fr.eirb.cassis.routes.{AuthenticationRoutes, ResourcesRoutes, ShorterRoutes, WelcomeRoutes}

object CassisMain extends cask.Main:

  override def host: String = "0.0.0.0"
  override def port: Int = 8080

  println("root@cassis.eirb.fr $ ./initialize")
  val allRoutes: Seq[cask.Routes] = Seq(AuthenticationRoutes(), ResourcesRoutes(), ShorterRoutes(), WelcomeRoutes())
  println("Initialized !")
