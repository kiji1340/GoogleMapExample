package com.dbao1608.googlemapexample.remotedata.repository

import androidx.lifecycle.MutableLiveData
import com.dbao1608.googlemapexample.Config
import com.dbao1608.googlemapexample.Key
import com.dbao1608.googlemapexample.remotedata.ApiClient
import com.dbao1608.googlemapexample.remotedata.api.DirectionApi
import com.dbao1608.googlemapexample.remotedata.reponse.DirectionResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DirectionRemoteRepository {
    companion object {
        private var googleMapRepository: DirectionRemoteRepository? = null

        fun getInstance(): DirectionRemoteRepository {
            if (googleMapRepository == null) {
                synchronized(this) {
                    googleMapRepository =
                        DirectionRemoteRepository()
                }

            }
            return googleMapRepository!!
        }
    }

    private var googleMapApi =
        ApiClient.createService(
            DirectionApi::class.java
        )

    fun getDirections(origin: String, destination: String, mode: String = "driving"): MutableLiveData<DirectionResponse> {
        val directionLiveData = MutableLiveData<DirectionResponse>()

        googleMapApi.getDirections(origin, destination, mode)
            .enqueue(object : Callback<DirectionResponse> {
                override fun onFailure(call: Call<DirectionResponse>, t: Throwable) {
                    directionLiveData.value = null
                }

                override fun onResponse(call: Call<DirectionResponse>, response: Response<DirectionResponse>) {
                    if(response.isSuccessful){
                        directionLiveData.value = response.body()
                    }
                }

            })

        return directionLiveData
    }

}
