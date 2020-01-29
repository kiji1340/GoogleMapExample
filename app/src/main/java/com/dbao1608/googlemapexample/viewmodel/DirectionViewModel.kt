package com.dbao1608.googlemapexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dbao1608.domain.entity.Direction
import com.dbao1608.domain.usecase.DirectionUseCase

class DirectionViewModel : BaseViewModel() {

    private var origin: String? = null
    private var destination: String? = null
    private val directionUseCase = DirectionUseCase()

    fun getDirections(isOrigin: Boolean, id: String): MutableLiveData<Direction> {
        val liveData = MutableLiveData<Direction>()
        if (isOrigin)
            origin = "place_id:$id"
        else
            destination = "place_id:$id"

        if (lifecycleOwner == null || context == null || origin == null || destination == null) {
            liveData.value = null
            return liveData
        }




        return directionUseCase.getDirections(origin!!, destination!!)
    }

    fun getDirections(mode: String): MutableLiveData<Direction> {
        val liveData = MutableLiveData<Direction>()
        if (lifecycleOwner == null || context == null || origin == null || destination == null) {
            liveData.value = null
            return liveData
        }


        return directionUseCase.getDirections(origin!!, destination!!, mode)
    }
}