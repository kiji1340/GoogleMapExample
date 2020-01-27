package com.dbao1608.googlemapexample.view.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dbao1608.googlemapexample.Key
import com.dbao1608.googlemapexample.R
import com.dbao1608.googlemapexample.model.Direction
import com.dbao1608.googlemapexample.view.controller.abstraction.Map
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*


class MapController(private val context: Context, private val callback: Map.Callback) :
    Map.Controller {
    private var locationPermissionGranted = false
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private var requestDialog: AlertDialog? = null
    private var googleMap: GoogleMap? = null


    override fun requestCurrentLocation(googleMap: GoogleMap) {
        this.googleMap = googleMap

        val checkSelPermission = ContextCompat.checkSelfPermission(
            context
            , android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (checkSelPermission == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
            getDeviceLocation()
        } else {
            ActivityCompat.requestPermissions(
                context as Activity
                , arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                , Key.PERMISSION_LOCATION_REQUEST_CODE
            )
        }
    }

    override fun getCurrentLocation() {
        if (googleMap == null) return
        getDeviceLocation()
    }

    override fun getRequestCurrentLocationResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            Key.PERMISSION_LOCATION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                    getDeviceLocation()
                }
            }
        }
    }

    override fun dismiss() {
        requestDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }


    override fun drawRoute(direction: Direction){
        if(googleMap == null) return
        googleMap!!.clear()

        addMarker(
            direction.startAddress!!.lat
            , direction.startAddress!!.lng
            , BitmapDescriptorFactory.fromResource(R.drawable.ic_location_pin_blue)
            , true
        )

        addMarker(
            direction.endAddress!!.lat
            , direction.endAddress!!.lng
            , BitmapDescriptorFactory.fromResource(R.drawable.ic_location_pin_red)
            , true
        )

        var points: ArrayList<LatLng?>
        var lineOptions: PolylineOptions? = null

        for (path in direction.routes) {
            points = ArrayList()
            lineOptions = PolylineOptions()

            for (j in path.indices) {
                val point = path[j]
                val lat = point["lat"]!!.toDouble()
                val lng = point["lng"]!!.toDouble()
                val position = LatLng(lat, lng)
                points.add(position)
            }

            lineOptions.addAll(points)
            lineOptions.width(10f)
            lineOptions.color(Color.RED)
        }

        if (lineOptions != null) {
            googleMap!!.addPolyline(lineOptions)
        }
    }

    private fun getDeviceLocation() {
        if (!locationPermissionGranted) return
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location == null) showRequestGPSDialog()
            else {
                googleMap?.clear()
                addMarker(
                    location.latitude
                    , location.longitude
                    , BitmapDescriptorFactory.fromResource(R.drawable.ic_place)
                    , true
                )
            }
        }
    }

    private fun showRequestGPSDialog() {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage(context.getString(R.string.question_request_gps))
            .setCancelable(false)
            .setPositiveButton(context.getString(R.string.btn_ok)) { dialog, id ->
                val callGPSSettingIntent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                context.startActivity(callGPSSettingIntent)
            }
        alertDialogBuilder.setNegativeButton(
            context.getString(R.string.btn_cancel)
        ) { dialog, id -> dialog.cancel() }
        requestDialog = alertDialogBuilder.create().apply {
            show()
        }
    }

    private fun addMarker(
        latitude: Double
        , longitude: Double
        , icon: BitmapDescriptor
        , hasMoveCamera: Boolean = false
    ) {
        if (googleMap == null) return

        val markerOptionsStart = MarkerOptions()
        markerOptionsStart.position(
            LatLng(latitude, longitude)
        )
        markerOptionsStart.icon(icon)
        googleMap!!.addMarker(markerOptionsStart)

        if (!hasMoveCamera) return
        googleMap!!.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(latitude, longitude)
                , 15f
            )
        )

    }


}