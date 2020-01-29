package com.dbao1608.remotedata.abstraction.repositories

import com.dbao1608.remotedata.reponse.DirectionResponse
import retrofit2.Call

interface DirectionRepository : RemoteRepository {
    fun getDirection(
        origin: String,
        destination: String,
        mode: String = "driving"
    ): Call<DirectionResponse>
}