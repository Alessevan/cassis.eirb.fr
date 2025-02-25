import mill._, scalalib._
import $ivy.`com.lihaoyi::mill-contrib-twirllib:`

object main extends ScalaModule with mill.twirllib.TwirlModule {

  def scalaVersion = "3.6.3"
  def twirlVersion = "2.1.0-M3"

  def generatedSources = T{ Seq(compileTwirl().classes) }

  override def ivyDeps = Agg(
    ivy"io.github.iltotore::iron::2.6.0",
    ivy"com.lihaoyi::cask:0.9.7",
    ivy"com.lihaoyi::requests:0.9.0",
    ivy"com.lihaoyi::scalatags:0.13.1",
    ivy"org.playframework.twirl::twirl-api::${twirlVersion()}"
  )
}