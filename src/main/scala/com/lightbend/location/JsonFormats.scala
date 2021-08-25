package com.lightbend.location

import com.lightbend.location.LocationRegistry.ActionPerformed
import com.lightbend.location.entity.{Location, Locations}
//import com.lightbend.location.entity.Locations
import spray.json.DefaultJsonProtocol


//#json-formats

object JsonFormats  {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val locationJsonFormat = jsonFormat4(Location)
  implicit val locationsJsonFormat = jsonFormat1(Locations)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
//#json-formats
