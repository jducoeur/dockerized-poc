package controllers

import play.api.mvc.{Action, Controller, EssentialAction}

import javax.inject.{Inject, Provider}

class HomeController @Inject() (val appProv: Provider[play.api.Application]) extends Controller {
  implicit lazy val app = appProv.get

  def hello(): EssentialAction = Action { implicit request =>
    Ok("Look, you called hello()")
  }
}
