package com.dbao1608.domain.usecase

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dbao1608.domain.abstraction.IPlaceUseCase
import com.dbao1608.domain.entity.Place
import com.dbao1608.domain.mapper.PlaceEntityMapper
import com.dbao1608.domain.mapper.PlaceMapper
import com.dbao1608.localdata.entity.PlaceEntity
import com.dbao1608.localdata.repository.PlaceLocalRepository
import com.dbao1608.remotedata.reponse.PlaceResponse
import com.dbao1608.remotedata.repository.PlaceRemoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceUseCase(context: Context): IPlaceUseCase {

    private val placeRemoteRepository = PlaceRemoteRepository()
    private val placeLocalRepository = PlaceLocalRepository(context)
    private val placeMapper = PlaceMapper.getInstance()


    override fun searchPlace(inputText: String): MutableLiveData<List<Place>>{
        val liveData = MutableLiveData<List<Place>>()

        placeRemoteRepository.getSearchPlace(inputText).enqueue(object: Callback<PlaceResponse>{
            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                liveData.value = null
            }

            override fun onResponse(call: Call<PlaceResponse>, response: Response<PlaceResponse>) {
                if(response.isSuccessful && response.body() != null){
                    val place = placeMapper.transformer(response.body()!!)
                    liveData.postValue(place)
                }

            }

        })

        return liveData
    }

    override fun insertPlace(place: Place) {
        Thread(Runnable {
            val placeEntity = PlaceEntityMapper().transformer(place)
            placeLocalRepository.insertPlace(placeEntity)
        }).start()
    }

    override fun getPlaceHistory(lifecycleOwner: LifecycleOwner): LiveData<List<Place>> {
        val liveData = MutableLiveData<List<Place>>()

        placeLocalRepository.getPlace()
            .observe(lifecycleOwner, Observer<List<PlaceEntity>> { placeEntities ->
                    val listData = ArrayList<Place>()

                    for(placeEntity in placeEntities){
                        listData.add(placeMapper.transformerFromCache(placeEntity))
                    }

                    liveData.postValue(listData)

                })

        return liveData
    }


}