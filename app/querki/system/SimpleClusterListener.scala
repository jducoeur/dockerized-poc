package querki.system

import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import akka.actor.ActorLogging
import akka.actor.Actor

class SimpleClusterListener extends Actor with ActorLogging {

  val cluster = Cluster(context.system)

  // subscribe to cluster changes, re-subscribe when restart
  override def preStart(): Unit = {
    //#subscribe
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents, classOf[MemberEvent], classOf[UnreachableMember])
    //#subscribe
  }
  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case MemberUp(member) =>
      println(s"Member is Up: ${member.address}")
    case UnreachableMember(member) =>
      println(s"Member detected as unreachable: $member")
    case MemberRemoved(member, previousStatus) =>
      println(s"Member is Removed: ${member.address} after $previousStatus")
    case other: MemberEvent =>
      println(s"Got some other member event: $other")
  }
}
