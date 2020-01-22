package com.dbao1608.googlemapexample.remotedata.repository

import androidx.lifecycle.MutableLiveData
import com.dbao1608.googlemapexample.remotedata.ApiClient
import com.dbao1608.googlemapexample.remotedata.api.PlaceApi
import com.dbao1608.googlemapexample.remotedata.reponse.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceRemoteRepository{
    companion object{
        private var placeRepository: PlaceRemoteRepository? = null

        fun getInstance(): PlaceRemoteRepository {
            if (placeRepository == null) {
                synchronized(this) {
                    placeRepository =
                        PlaceRemoteRepository()
                }

            }
            return placeRepository!!
        }
    }

    private val placeApi =
        ApiClient.createService(
            PlaceApi::class.java
        )

    fun searchPlace(inputText: String): MutableLiveData<PlaceResponse>{
        val result = MutableLiveData<PlaceResponse>()
        placeApi.searchPlace(inputText)
            .enqueue(object: Callback<PlaceResponse>{
                override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                    result.value = null
                }

                override fun onResponse(
                    call: Call<PlaceResponse>,
                    response: Response<PlaceResponse>
                ) {
                    if(response.isSuccessful){
                        result.value = response.body()
                    }
                }

            })
        return result
    }
}