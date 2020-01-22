package com.dbao1608.googlemapexample.remotedata.api

import com.dbao1608.googlemapexample.remotedata.reponse.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceApi {
    @GET("place/findplacefromtext/json")
    fun searchPlace(
        @Query("input") input: String
        , @Query("inputtype") inputtype: String = "textquery"
        , @Query("fields") fields: String = "photos,formatted_address,name,geometry,place_id"
    ): Call<PlaceResponse>
}