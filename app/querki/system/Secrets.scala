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
 *
 * Note that this code is loosely adapted from the suggested Java code example provided by AWS Secrets Manager
 * when I created the POC secret.
 */
object Secrets {

  val secretName = "querki/poc/secrets"
  val region = "us-east-1"

  // Grungy but practical way to get the endpoint from the environment if it is defined.
  // In theory, this should come via application.conf; in practice, that isn't working for me. Why not?
  def secretsEndpoint: Option[String] = sys.env.get("SECRETS_ENDPOINT")

  lazy val secretsManager = {
    val base = AWSSecretsManagerClientBuilder
      .standard()
    val configured = secretsEndpoint.map { endpoint =>
      // If there is a configured endpoint, we're in local development, and need to point to that:
      base.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
        endpoint,
        region
      ))
    }.getOrElse {
      // Actually in AWS, so we don't need to specify the endpoint (?):
      base.withRegion(region)
    }
    configured.build()
  }

  // Yes, this is horrible and Java-y and blocking and mutating. But it's throwaway test setup code.
  def getSecret(name: String): String = {
    val request = new GetSecretValueRequest()
    request.setSecretId(name)
    val result = secretsManager.getSecretValue(request)
    result.getSecretString()
  }

}
