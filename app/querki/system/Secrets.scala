package querki.system

import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest

/**
 * Interface to the AWS Secrets Manager.
 *
 * NOTE: in the real Querki environment, this probably should be an Ecot, but note that it needs to exist
 * *extremely* early -- we need to be able to access secrets in order to build the Play application itself (to
 * get at the app secret). So see if the Ecology is built early enough. For purposes of this example, I'm not
 * worrying about it.
 */
object Secrets {

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
  def getSecret(name: String): String = {
    val request = new GetSecretValueRequest()
    request.setSecretId(name)
    val result = secretsManager.getSecretValue(request)
    result.getSecretString()
  }

}
