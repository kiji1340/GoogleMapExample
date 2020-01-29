package com.dbao1608.googlemapexample.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dbao1608.domain.DomainConfig
import com.dbao1608.domain.entity.Direction
import com.dbao1608.domain.entity.Place
import com.dbao1608.googlemapexample.R
import com.dbao1608.googlemapexample.view.controller.MapController
import com.dbao1608.googlemapexample.view.controller.abstraction.Map
import com.dbao1608.googlemapexample.view.custom.MapBottomListener
import com.dbao1608.googlemapexample.view.custom.SearchViewListener
import com.dbao1608.googlemapexample.viewmodel.DirectionViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.view_map_bottom_controller.view.*


class MapsActivity : AppCompatActivity()
    , OnMapReadyCallback
    , Map.Callback
    , MapBottomListener.TextView
    , MapBottomListener.ModeMoving
    , SearchViewListener.BackButton {

    private lateinit var mMap: GoogleMap
    private var directionViewModel: DirectionViewModel? = null
    private lateinit var mapController: MapController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        DomainConfig.setKeyApi(getString(R.string.google_maps_key))
        mapController = MapController(this, this)


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        directionViewModel = ViewModelProvider(this)[DirectionViewModel::class.java]
        directionViewModel!!.apply {
            attach(this@MapsActivity)
            addContext(this@MapsActivity)
        }

        bottomController.setOnTextViewEvent(this)
        bottomController.setOnModeMovingEvent(this)

        searchView.setOnBackPressedListener(this)
        searchView.attachOnActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapController.dismiss()
    }

    override fun onResume() {
        super.onResume()
        mapController.getCurrentLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mapController.getRequestCurrentLocationResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mapController.requestCurrentLocation(mMap)
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
            if (isOrigin)
                bottomController.startTxt.text = it.address
            else
                bottomController.endTxt.text = it.address

            directionViewModel?.getDirections(isOrigin, it.id!!)
                ?.observe(this
                    , Observer<Direction> { direction: Direction? ->drawDirection(direction) })
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
        directionViewModel?.getDirections(mode)?.observe(this,
            Observer<Direction> { direction: Direction? -> drawDirection(direction) })
    }

    private fun drawDirection(direction: Direction?){
        if (direction == null){
            return
        }
        mapController.drawRoute(direction)
    }


}
