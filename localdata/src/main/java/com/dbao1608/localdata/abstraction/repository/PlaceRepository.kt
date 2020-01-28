package com.dbao1608.localdata.abstraction.repository

import androidx.lifecycle.LiveData
import com.dbao1608.localdata.entity.PlaceEntity

interface PlaceRepository : LocalRepository {
    fun insertPlace(placeEntity: PlaceEntity)

    fun getPlace(): LiveData<List<PlaceEntity>>
}