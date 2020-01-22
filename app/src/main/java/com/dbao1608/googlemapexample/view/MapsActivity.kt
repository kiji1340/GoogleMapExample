package com.dbao1608.googlemapexample.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dbao1608.googlemapexample.Config
import com.dbao1608.googlemapexample.R
import com.dbao1608.googlemapexample.model.Direction
import com.dbao1608.googlemapexample.model.Place
import com.dbao1608.googlemapexample.view.custom.MapBottomListener
import com.dbao1608.googlemapexample.view.custom.SearchViewListener
import com.dbao1608.googlemapexample.viewmodel.DirectionViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.view_map_bottom_controller.view.*


class MapsActivity : AppCompatActivity()
    , OnMapReadyCallback
    , MapBottomListener.TextView
    , MapBottomListener.ModeMoving
    , SearchViewListener.BackButton {

    private lateinit var mMap: GoogleMap
    private var directionViewModel: DirectionViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        Config.initialize(this.applicationContext)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        directionViewModel = ViewModelProviders.of(this)[DirectionViewModel::class.java]
        directionViewModel!!.apply {
            attach(this@MapsActivity)
            addContext(this@MapsActivity)
        }

        bottomController.setOnTextViewEvent(this)
        bottomController.setOnModeMovingEvent(this)

        searchView.setOnBackPressedListener(this)
        searchView.attachOnActivity(this)
    }

    private fun drawRoute(direction: Direction) {
        mMap.clear()

        val markerOptionsStart = MarkerOptions()
        markerOptionsStart.position(
            LatLng(
                direction.startAddress!!.lat
                , direction.startAddress!!.lng))
        markerOptionsStart.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_pin_blue))
        mMap.addMarker(markerOptionsStart)

        mMap.moveCamera(
            CameraUpdateFactory.newLatLng(
                LatLng(
                    direction.startAddress!!.lat
                    , direction.startAddress!!.lng
                )
            )
        )

        val markerOptionsEnd = MarkerOptions()
        markerOptionsEnd.position(
            LatLng(
                direction.endAddress!!.lat
                , direction.endAddress!!.lng))
        markerOptionsEnd.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_pin_red))
        mMap.addMarker(markerOptionsEnd)

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
            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points)
            lineOptions.width(10f)
            lineOptions.color(Color.RED)
        }

        if (lineOptions != null) {
            mMap.addPolyline(lineOptions)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun onTextViewEvent(isOrigin: Boolean) {
        searchView.isOrigin = isOrigin
        ValueAnimator.ofFloat(container.width.toFloat(), 0f).apply {
            duration = 250
            addUpdateListener {
                searchView.translationX = it.animatedValue as Float
            }

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    searchView.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    removeAllUpdateListeners()
                }
            })

            start()
        }
    }

    override fun onBackPressedEvent(isOrigin: Boolean, data: Place?) {
        data?.let {
            if(isOrigin)
                bottomController.startTxt.text = it.address
            else
                bottomController.endTxt.text = it.address


            directionViewModel?.getDirections(isOrigin, it.id!!)?.observe(this, object: Observer<Direction>{
                override fun onChanged(t: Direction?) {
                    if(t == null) return
                    drawRoute(t)
                }

            })
        }


        ValueAnimator.ofFloat(0f, container.width.toFloat()).apply {
            duration = 250
            addUpdateListener {
                searchView.translationX = it.animatedValue as Float
            }

            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    searchView.visibility = View.GONE
                    removeAllUpdateListeners()
                }
            })

            start()
        }
    }

    override fun onModeEvent(mode: String) {
        directionViewModel?.getDirections(mode)?.observe(this, object: Observer<Direction>{
            override fun onChanged(t: Direction?) {
                if(t == null) return
                drawRoute(t)
            }

        })
    }
}
