package querki.system

import play.api.inject.guice.{GuiceApplicationBuilder, GuiceApplicationLoader}
import play.api.{Application, ApplicationLoader}

class QuerkiApplicationLoader extends ApplicationLoader {

  def load(context: ApplicationLoader.Context): Application = {
    val newConfig = context.initialConfiguration
    val newContext = context.copy(initialConfiguration = newConfig)
    val builder = new GuiceApplicationBuilder()
    val app = (new GuiceApplicationLoader(builder)).load(newContext)
    app
  }
}
