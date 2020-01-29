package com.dbao1608.googlemapexample.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dbao1608.domain.entity.Place
import com.dbao1608.domain.usecase.PlaceUseCase

class PlaceViewModel : BaseViewModel() {

    private lateinit var placeUseCase: PlaceUseCase

    override fun addContext(context: Context) {
        super.addContext(context)
        placeUseCase = PlaceUseCase(context)
    }

    fun searchPlace(input: String): MutableLiveData<List<Place>> {
        return placeUseCase.searchPlace(input)
    }

    fun insertPlaceCache(place: Place) {
        Thread(Runnable {
            placeUseCase.insertPlace(place)
        }).start()
    }

    fun getPlaceHistory(): LiveData<List<Place>> {
        val liveData = MutableLiveData<List<Place>>()

        if (lifecycleOwner == null) {
            liveData.postValue(ArrayList())
            return liveData
        }



        return placeUseCase.getPlaceHistory(lifecycleOwner!!)
    }

}