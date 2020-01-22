package com.dbao1608.googlemapexample.remotedata.reponse

data class DirectionResponse(
    val geocoded_waypoints: List<GeocodedWaypointResponse>,
    val routes: List<RouteResponse>,
    val status: String
)

data class GeocodedWaypointResponse(
    val geocoder_status: String,
    val place_id: String,
    val types: List<String>
)

data class RouteResponse(
    val bounds: BoundsResponse,
    val copyrights: String,
    val legs: List<LegResponse>,
    val overview_polyline: OverviewPolylineResponse,
    val summary: String,
    val warnings: List<Any>,
    val waypoint_order: List<Any>
)

data class BoundsResponse(
    val northeast: NortheastResponse,
    val southwest: SouthwestResponse
)





data class LegResponse(
    val distance: DistanceResponse,
    val duration: DurationResponse,
    val end_address: String,
    val end_location: EndLocationResponse,
    val start_address: String,
    val start_location: StartLocationResponse,
    val steps: List<StepResponse>,
    val traffic_speed_entry: List<Any>,
    val via_waypoint: List<Any>
)

data class DistanceResponse(
    val text: String,
    val value: Int
)

data class DurationResponse(
    val text: String,
    val value: Int
)

data class EndLocationResponse(
    val lat: Double,
    val lng: Double
)

data class StartLocationResponse(
    val lat: Double,
    val lng: Double
)

data class StepResponse(
    val distance: DistanceXResponse,
    val duration: DurationXResponse,
    val end_location: EndLocationXResponse,
    val html_instructions: String,
    val maneuver: String,
    val polyline: PolylineResponse,
    val start_location: StartLocationXResponse,
    val travel_mode: String
)

data class DistanceXResponse(
    val text: String,
    val value: Int
)

data class DurationXResponse(
    val text: String,
    val value: Int
)

data class EndLocationXResponse(
    val lat: Double,
    val lng: Double
)

data class PolylineResponse(
    val points: String
)

data class StartLocationXResponse(
    val lat: Double,
    val lng: Double
)

data class OverviewPolylineResponse(
    val points: String
)