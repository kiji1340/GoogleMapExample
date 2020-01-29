package com.dbao1608.remotedata.repository

import com.dbao1608.remotedata.ApiClient
import com.dbao1608.remotedata.api.DirectionApi
import com.dbao1608.remotedata.reponse.DirectionResponse
import com.dbao1608.remotedata.abstraction.repositories.DirectionRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DirectionRemoteRepository: DirectionRepository {

    private val directionApi= ApiClient.createService(DirectionApi::class.java)

    override fun getDirection(
        origin: String,
        destination: String,
        mode: String
    ): Call<DirectionResponse> = directionApi.getDirections(origin, destination, mode)






}
