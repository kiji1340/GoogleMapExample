package com.dbao1608.googlemapexample.view.controller.abstraction

import com.dbao1608.domain.entity.Direction
import com.google.android.gms.maps.GoogleMap

interface Map {
    interface Controller : BaseAbstraction.Controller{
        fun requestCurrentLocation(googleMap: GoogleMap)
        fun getCurrentLocation()
        fun getRequestCurrentLocationResult(
            requestCode: Int
            , permissions: Array<out String>
            , grantResults: IntArray)
        fun drawRoute(direction: Direction)
    }

    interface Callback {

    }
}