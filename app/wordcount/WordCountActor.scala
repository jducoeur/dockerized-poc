package wordcount

import akka.actor.Actor
import akka.cluster.sharding.ShardRegion
import wordcount.WordCountActor._

class WordCountActor extends Actor {
  var count: Int = 0

  def receive: Receive = {
    case WordCalled(word) => {
      count += 1
      sender() ! CallCount(word, count)
    }
  }
}

object WordCountActor {
  case class WordCalled(word: String)

  case class CallCount(
    word: String,
    count: Int
  )

  val extractEntityId: ShardRegion.ExtractEntityId = {
    case msg @ WordCalled(word) => (word, msg)
  }

  val extractShardId: ShardRegion.ExtractShardId = {
    case WordCalled(word) => (word.hashCode % 30).toString
  }
}
