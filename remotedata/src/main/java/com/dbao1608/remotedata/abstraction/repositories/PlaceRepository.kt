package com.dbao1608.remotedata.abstraction.repositories

import com.dbao1608.remotedata.reponse.PlaceResponse
import retrofit2.Call

interface PlaceRepository :
    RemoteRepository {
    fun getSearchPlace(inputText: String): Call<PlaceResponse>
}