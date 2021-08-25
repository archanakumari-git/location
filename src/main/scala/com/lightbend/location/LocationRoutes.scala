package com.lightbend.location

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.actor.typed.scaladsl.AskPattern._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{path, _}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.lightbend.location.LocationRegistry._
import com.lightbend.location.entity.{Location, Locations}

import scala.concurrent.Future

class LocationRoutes(locationRegistry: ActorRef[LocationRegistry.Command])
                    (implicit val system: ActorSystem[_]) {

  //#location-routes-class
  //#import-json-formats

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import JsonFormats._

  // If ask takes more time than this to complete the request is failed
  private implicit val timeout = Timeout.create(system.settings.config.getDuration("my-app.routes.ask-timeout"))

  def getLocations(): Future[Locations] =
    locationRegistry.ask(GetLocations)

  def getLocation(city: String): Future[GetLocationResponse] =
    locationRegistry.ask(GetLocation(city, _))

  def getLocationByZipCode(zipCode: Int): Future[GetLocationResponse] =
    locationRegistry.ask(GetLocationByZipCode(zipCode, _))

  def createLocation(location: Location): Future[ActionPerformed] =
    locationRegistry.ask(CreateLocation(location, _))

  def deleteLocation(city: String): Future[ActionPerformed] =
    locationRegistry.ask(DeleteLocation(city, _))

  //#all-routes
  val locationRoutes: Route =
    pathPrefix("locations") {
      concat(
        pathEnd {
          concat(
            get {
              complete(getLocations())
            },
            post {
              entity(as[Location]) { location =>
                onSuccess(createLocation(location)) { performed =>
                  complete((StatusCodes.Created, performed))
                }
              }
            })
        },
        path("zipcode" / IntNumber) { zipcode =>
          get {
            rejectEmptyResponse {
              onSuccess(getLocationByZipCode(zipcode)) { response =>
                complete(response.maybeLocation)
              }
            }
          }
        },
        path(Segment) { city =>
          concat(
            get {
              rejectEmptyResponse {
                onSuccess(getLocation(city)) { response =>
                  complete(response.maybeLocation)
                }
              }
            },
            delete {
              onSuccess(deleteLocation(city)) { performed =>
                complete((StatusCodes.OK, performed))
              }
            })
        }
      )
    }
}
