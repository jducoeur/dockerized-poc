package controllers

import com.amazonaws.client.builder.AwsClientBuilder
import play.api.mvc.{Action, Controller, EssentialAction}

import java.net.InetAddress
import javax.inject.{Inject, Provider}
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest
import play.api.Logger

class HomeController @Inject() (val appProv: Provider[play.api.Application]) extends Controller {
  implicit lazy val app = appProv.get

  lazy val secretsManager =
    AWSSecretsManagerClientBuilder
      .standard()
      // "host.docker.internal" is how we break out of our Docker container, to use localhost for localstack.
      // Port 4566 is localstack.
      // Need to explicitly use http, or it will try to use https and get confused.
      // TODO: need to make this configurable, so that it can work for real when deployed:
      .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
        "http://host.docker.internal:4566",
        "us-east-1"
      ))
      .build()

  // Yes, this is horrible and Java-y and blocking and mutating. But it's throwaway test setup code.
  lazy val theSecret: String = {
    val request = new GetSecretValueRequest()
    request.setSecretId("test_secret")
    val result = secretsManager.getSecretValue(request)
    result.getSecretString()
  }

  def hello(): EssentialAction = Action { implicit request =>
    val localAddr = InetAddress.getLocalHost.getHostAddress
    Logger.info(s"Got a call on $localAddr")
    Ok(s"Look, you called hello() on $localAddr. My secret is $theSecret")
  }
}
