package controllers

import play.api.mvc.{Action, Controller, EssentialAction}

import java.net.InetAddress
import javax.inject.{Inject, Provider}

class HomeController @Inject() (val appProv: Provider[play.api.Application]) extends Controller {
  implicit lazy val app = appProv.get

  def hello(): EssentialAction = Action { implicit request =>
    val localAddr = InetAddress.getLocalHost.getHostAddress
    Ok(s"Look, you called hello() on $localAddr")
  }
}
