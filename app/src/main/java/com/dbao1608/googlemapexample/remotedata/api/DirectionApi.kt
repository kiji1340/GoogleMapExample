package com.dbao1608.googlemapexample.remotedata.api

import com.dbao1608.googlemapexample.remotedata.reponse.DirectionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionApi {
    @GET("directions/json")
    fun getDirections(
        @Query("origin") origin: String
        , @Query("destination") destination: String
        , @Query("mode") mode: String
    ): Call<DirectionResponse>
}