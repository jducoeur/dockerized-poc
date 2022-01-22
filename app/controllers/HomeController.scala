package controllers

import scala.concurrent.duration._
import akka.actor.{ActorRef, Props}
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings}
import akka.pattern.ask
import akka.util.Timeout
import play.api.mvc.{Action, Controller, EssentialAction}

import java.net.InetAddress
import javax.inject.{Inject, Provider}
import play.api.Logger
import querki.system.{PocCluster, Secrets}
import wordcount.WordCountActor

import scala.concurrent.ExecutionContext

class HomeController @Inject() (
  val appProv: Provider[play.api.Application]
) extends Controller {
  implicit lazy val app = appProv.get

  lazy val theSecret: String = Secrets.getSecret("test_secret")

  def hello(): EssentialAction = Action { implicit request =>
    val localAddr = InetAddress.getLocalHost.getHostAddress
    Logger.info(s"Got a call on $localAddr")
    Ok(s"Look, you called hello() on $localAddr. My secret is $theSecret")
  }

  // All of this startup stuff is horribly non-deterministic, and should not be used as an example of good
  // practice! (But it's convenient for a slash-and-burn PoC.)
  lazy val actorSystem = PocCluster.actorSystem

  lazy val counterRegion: ActorRef = ClusterSharding(actorSystem).start(
    typeName = "Counter",
    entityProps = Props[WordCountActor],
    settings = ClusterShardingSettings(actorSystem),
    extractEntityId = WordCountActor.extractEntityId,
    extractShardId = WordCountActor.extractShardId
  )

  implicit val callTimeout: Timeout = Timeout(10.seconds)
  implicit val ec: ExecutionContext = actorSystem.dispatcher

  def countWord(word: String): EssentialAction = Action.async { implicit request =>
    (counterRegion ? WordCountActor.WordCalled(word)).map { case WordCountActor.CallCount(wordRet, count) =>
      Ok(s"You have said $wordRet $count times so far")
    }
  }
}
