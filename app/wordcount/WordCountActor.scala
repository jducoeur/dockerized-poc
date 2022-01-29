package wordcount

import akka.actor.Actor
import akka.cluster.sharding.ShardRegion
import wordcount.WordCountActor._

import java.net.InetAddress

class WordCountActor extends Actor {
  var count: Int = 0

  lazy val localAddr = InetAddress.getLocalHost.getHostAddress

  def receive: Receive = {
    case WordCalled(word) => {
      count += 1
      sender() ! CallCount(word, count, localAddr)
    }
  }
}

object WordCountActor {
  case class WordCalled(word: String)

  case class CallCount(
    word: String,
    count: Int,
    actorAddr: String
  )

  val extractEntityId: ShardRegion.ExtractEntityId = {
    case msg @ WordCalled(word) => (word, msg)
  }

  val extractShardId: ShardRegion.ExtractShardId = {
    case WordCalled(word) => (word.hashCode % 30).toString
  }
}
