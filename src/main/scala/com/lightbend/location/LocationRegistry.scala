package com.lightbend.location

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.lightbend.location.entity.{Location, Locations}

//#location-registry-actor

object LocationRegistry {
  sealed trait Command

  final case class GetLocations(replyTo: ActorRef[Locations]) extends Command

  final case class CreateLocation(location: Location, replyTo: ActorRef[ActionPerformed]) extends Command

  final case class GetLocation(city: String, replyTo: ActorRef[GetLocationResponse]) extends Command

  final case class GetLocationByZipCode(zipCode: Int, replyTo: ActorRef[GetLocationResponse]) extends Command

  final case class DeleteLocation(city: String, replyTo: ActorRef[ActionPerformed]) extends Command

  final case class GetLocationResponse(maybeLocation: Option[Location])

  final case class ActionPerformed(description: String)

  def apply(): Behavior[Command] = registry(Set.empty)

  private def registry(locations: Set[Location]): Behavior[Command] =
    Behaviors.receiveMessage {
      case GetLocations(replyTo) =>
        replyTo ! Locations(locations.toSeq)
        Behaviors.same
      case CreateLocation(location, replyTo) =>
        replyTo ! ActionPerformed(s"Location ${location.city} created.")
        registry(locations + location)
      case GetLocation(city, replyTo) =>
        replyTo ! GetLocationResponse(locations.find(_.city == city))
        Behaviors.same
      case GetLocationByZipCode(zipCode, replyTo) =>
        replyTo ! GetLocationResponse(locations.find(_.zipCode == zipCode))
        Behaviors.same
      case DeleteLocation(city, replyTo) =>
        replyTo ! ActionPerformed(s"Location $city deleted.")
        registry(locations.filterNot(_.city == city))
    }
}
//#location-registry-actor
