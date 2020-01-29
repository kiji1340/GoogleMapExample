package com.dbao1608.remotedata.repository

import com.dbao1608.remotedata.ApiClient
import com.dbao1608.remotedata.api.PlaceApi
import com.dbao1608.remotedata.reponse.PlaceResponse
import com.dbao1608.remotedata.abstraction.repositories.PlaceRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceRemoteRepository : PlaceRepository {

    private val placeApi= ApiClient.createService(PlaceApi::class.java)



    override fun getSearchPlace(inputText: String): Call<PlaceResponse> {
        return placeApi.searchPlace(inputText)
    }


}