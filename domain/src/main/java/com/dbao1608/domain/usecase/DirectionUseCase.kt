package com.dbao1608.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.dbao1608.domain.abstraction.IDirectionUseCase
import com.dbao1608.domain.entity.Direction
import com.dbao1608.domain.mapper.DirectionMapper
import com.dbao1608.remotedata.reponse.DirectionResponse
import com.dbao1608.remotedata.repository.DirectionRemoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DirectionUseCase : IDirectionUseCase {
    private val directionRepository = DirectionRemoteRepository()


    override fun getDirections(
        origin: String
        , destination: String
        , mode: String
    ): MutableLiveData<Direction> {
        val liveData = MutableLiveData<Direction>()
        directionRepository.getDirection(origin, destination, mode)
            .enqueue(object : Callback<DirectionResponse> {
                override fun onFailure(call: Call<DirectionResponse>, t: Throwable) {
                    liveData.postValue(null)
                }

                override fun onResponse(
                    call: Call<DirectionResponse>,
                    response: Response<DirectionResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.routes.isNullOrEmpty())
                            liveData.postValue(null)
                        else
                            liveData
                                .postValue(
                                    DirectionMapper().transformer(response.body()!!)
                                )

                    } else {
                        liveData.postValue(null)
                    }
                }

            })
        return liveData
    }
}