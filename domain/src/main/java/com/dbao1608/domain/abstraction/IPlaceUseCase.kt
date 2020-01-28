package com.dbao1608.domain.abstraction

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dbao1608.domain.entity.Place

interface IPlaceUseCase {
    fun searchPlace(inputText: String): MutableLiveData<List<Place>>
    fun insertPlace(place: Place)
    fun getPlaceHistory(lifecycleOwner: LifecycleOwner): LiveData<List<Place>>
}