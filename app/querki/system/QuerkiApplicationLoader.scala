package querki.system

import play.api.inject.guice.{GuiceApplicationBuilder, GuiceApplicationLoader}
import play.api.{Application, ApplicationLoader, Configuration}

class QuerkiApplicationLoader extends ApplicationLoader {

  def load(context: ApplicationLoader.Context): Application = {
    val appSecret = Secrets.getSecret("app_secret")
    // Note that play.crypto.secret changes name in newer versions of Play:
    val secretsConfig: Configuration = Configuration("play.crypto.secret" -> appSecret)

    // Note that ++ is replaced (and flipped in order) by withFallback() in newer versions of Play:
    val newConfig = context.initialConfiguration ++ secretsConfig
    val newContext = context.copy(initialConfiguration = newConfig)
    val builder = new GuiceApplicationBuilder()
    val app = (new GuiceApplicationLoader(builder)).load(newContext)
    PocCluster.init(app)
    app
  }
}
