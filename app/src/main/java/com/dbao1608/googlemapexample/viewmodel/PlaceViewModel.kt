package com.dbao1608.googlemapexample.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dbao1608.googlemapexample.localdata.PlaceEntity
import com.dbao1608.googlemapexample.localdata.PlaceLocalRepository
import com.dbao1608.googlemapexample.mapper.PlaceEntityMapper
import com.dbao1608.googlemapexample.mapper.PlaceMapper
import com.dbao1608.googlemapexample.model.Place
import com.dbao1608.googlemapexample.remotedata.reponse.PlaceResponse
import com.dbao1608.googlemapexample.remotedata.repository.PlaceRemoteRepository

class PlaceViewModel : BaseViewModel() {

    private var placeLocalRepository: PlaceLocalRepository? = null
    private val placeRepository = PlaceRemoteRepository.getInstance()
    private val placeMapper = PlaceMapper.getInstance()

    override fun addContext(context: Context) {
        super.addContext(context)
        placeLocalRepository = PlaceLocalRepository(context)
    }
    fun searchPlace(input: String): MutableLiveData<List<Place>> {
        val mutableLiveData = MutableLiveData<List<Place>>()

        if (lifecycleOwner == null) {
            mutableLiveData.postValue(null)
            return mutableLiveData
        }

        placeRepository.searchPlace(input).observe(lifecycleOwner!!,
            Observer<PlaceResponse> { t ->
                if (t == null) {
                    mutableLiveData.postValue(null)
                } else {
                    mutableLiveData.postValue(placeMapper.transformer(t))
                }
            })
        return mutableLiveData
    }

    fun insertPlaceCache(place: Place) {
        Thread(Runnable {
            val placeEntity = PlaceEntityMapper().transformer(place)
            placeLocalRepository?.insertPlaceChoose(placeEntity)
        }).start()
    }

    fun getPlaceHistory(): LiveData<List<Place>> {
        val liveData = MutableLiveData<List<Place>>()

        if (lifecycleOwner == null || placeLocalRepository == null) {
            liveData.postValue(ArrayList())
            return liveData
        }

        placeLocalRepository!!.getHistory()
            .observe(lifecycleOwner!!
                , Observer<List<PlaceEntity>> { placeEntities ->
                    val listData = ArrayList<Place>()

                    for(placeEntity in placeEntities){
                        listData.add(placeMapper.transformerFromCache(placeEntity))
                    }

                    liveData.postValue(listData)

                })

        return liveData
    }

}