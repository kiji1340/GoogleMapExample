package com.dbao1608.domain.abstraction

import androidx.lifecycle.MutableLiveData
import com.dbao1608.domain.entity.Direction

interface IDirectionUseCase {
    fun getDirections(
        origin: String
        , destination: String
        , mode: String = "driving"
    ): MutableLiveData<Direction>
}