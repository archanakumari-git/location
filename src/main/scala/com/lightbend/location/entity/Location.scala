package com.lightbend.location.entity

import scala.collection.immutable

//#location-case-classes
final case class Location(zipCode: Int, city: String, state: String, country : String)

final case class Locations(locations: immutable.Seq[Location])
