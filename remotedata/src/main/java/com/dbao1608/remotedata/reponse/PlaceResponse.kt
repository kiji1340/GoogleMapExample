package com.dbao1608.remotedata.reponse

data class PlaceResponse(
    val candidates: List<CandidateResponse>,
    val status: String
)

data class CandidateResponse(
    val formatted_address: String,
    val geometry: GeometryResponse,
    val name: String,
    val photos: List<PhotoResponse>,
    val place_id: String
)

data class GeometryResponse(
    val location: LocationResponse,
    val viewport: ViewportResponse
)

data class LocationResponse(
    val lat: Double,
    val lng: Double
)

data class ViewportResponse(
    val northeast: NortheastResponse,
    val southwest: SouthwestResponse
)

data class NortheastResponse(
    val lat: Double,
    val lng: Double
)

data class SouthwestResponse(
    val lat: Double,
    val lng: Double
)

data class PhotoResponse(
    val height: Int,
    val html_attributions: List<String>,
    val photo_reference: String,
    val width: Int
)