package controllers

import play.api.mvc.{Action, Controller, EssentialAction}

import java.net.InetAddress
import javax.inject.{Inject, Provider}
import play.api.Logger
import querki.system.Secrets

class HomeController @Inject() (val appProv: Provider[play.api.Application]) extends Controller {
  implicit lazy val app = appProv.get

  lazy val theSecret: String = Secrets.getSecret("test_secret")

  def hello(): EssentialAction = Action { implicit request =>
    val localAddr = InetAddress.getLocalHost.getHostAddress
    Logger.info(s"Got a call on $localAddr")
    Ok(s"Look, you called hello() on $localAddr. My secret is $theSecret")
  }
}
