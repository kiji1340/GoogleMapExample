package com.dbao1608.googlemapexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dbao1608.googlemapexample.mapper.DirectionMapper
import com.dbao1608.googlemapexample.model.Direction
import com.dbao1608.googlemapexample.remotedata.reponse.DirectionResponse
import com.dbao1608.googlemapexample.remotedata.repository.DirectionRemoteRepository

class DirectionViewModel : BaseViewModel() {

    private var mapRepository: DirectionRemoteRepository =
        DirectionRemoteRepository.getInstance()
    private var origin: String? = null
    private var destination: String? = null

    fun getDirections(isOrigin: Boolean, id: String): MutableLiveData<Direction> {
        val liveData = MutableLiveData<Direction>()
        if(isOrigin)
            origin = "place_id:$id"
        else
            destination = "place_id:$id"

        if (lifecycleOwner == null || context == null || origin == null || destination == null) {
            liveData.value = null
            return liveData
        }



        mapRepository.getDirections(origin!!, destination!!).observe(lifecycleOwner!!
            , Observer<DirectionResponse> { response ->
                response?.let {
                    liveData.postValue(DirectionMapper().transformer(it))
                }
            })

        return liveData
    }

    fun getDirections(mode: String): MutableLiveData<Direction>{
        val liveData = MutableLiveData<Direction>()
        if (lifecycleOwner == null || context == null || origin == null || destination == null) {
            liveData.value = null
            return liveData
        }
        mapRepository.getDirections(origin!!, destination!!, mode = mode).observe(lifecycleOwner!!
            , Observer<DirectionResponse> { response ->
                response?.let {
                    liveData.postValue(DirectionMapper().transformer(it))
                }
            })

        return liveData
    }
}