package com.dbao1608.domain.mapper

import com.dbao1608.domain.entity.Address
import com.dbao1608.domain.entity.Direction
import com.dbao1608.domain.entity.LatLng
import com.dbao1608.remotedata.reponse.DirectionResponse


class DirectionMapper : Mapper<DirectionResponse, Direction> {
    override fun transformer(type: DirectionResponse): Direction {
        val direction = Direction()

        val startAddress = Address().apply {
            id = type.geocoded_waypoints[0].place_id
        }
        val endAddress = Address().apply {
            id = type.geocoded_waypoints[1].place_id
        }

        for (route in type.routes) {
            val path = ArrayList<HashMap<String, String>>()
            for (leg in route.legs) {
                startAddress.apply {
                    name = leg.start_address
                    lng = leg.start_location.lng
                    lat = leg.start_location.lat
                }

                endAddress.apply {
                    name = leg.end_address
                    lng = leg.end_location.lng
                    lat = leg.end_location.lat
                }
                direction.startAddress = startAddress
                direction.endAddress = endAddress
                for (step in leg.steps) {
                    val listPoly = decodePoly(step.polyline.points)
                    listPoly?.let {
                        for (latLng in it) {
                            val hm: HashMap<String, String> = HashMap()
                            hm["lat"] = latLng.latitude.toString()
                            hm["lng"] = latLng.longitude.toString()
                            path.add(hm)
                        }
                    }
                }
            }
            direction.routes.add(path)
        }

        return direction
    }


    private fun decodePoly(encoded: String): List<LatLng>? {
        val len: Int = encoded.length

        val path: MutableList<LatLng> = ArrayList()
        var index = 0
        var lat = 0
        var lng = 0

        while (index < len) {
            var result = 1
            var shift = 0
            var b: Int
            do {
                b = encoded[index++].toInt() - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 0x1f)
            lat += if (result and 1 != 0) (result shr 1).inv() else result shr 1
            result = 1
            shift = 0
            do {
                b = encoded[index++].toInt() - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 0x1f)
            lng += if (result and 1 != 0) (result shr 1).inv() else result shr 1
            path.add(LatLng(lat * 1e-5, lng * 1e-5))
        }

        return path
    }
}