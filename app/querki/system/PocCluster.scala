package querki.system

import akka.actor.{ActorRef, ActorSystem, Props}
import play.api.Application

/**
 * Horrible global variables for the PoC.
 */
object PocCluster {
  var _actorSystem: Option[ActorSystem] = None

  def actorSystem =
    _actorSystem.getOrElse(throw new Exception(s"Trying to access the PoC actorSystem before it was initialized!"))

  var _clusterListener: ActorRef = null

  def init(app: Application): ActorSystem = {
    val config = app.configuration
    val as = ActorSystem(
      name = config.getString("clustering.cluster.name").get,
      config = Some(config.underlying),
      classLoader = Some(app.classloader)
    )
    _actorSystem = Some(as)
    _clusterListener = as.actorOf(Props[SimpleClusterListener])
    as
  }
}
