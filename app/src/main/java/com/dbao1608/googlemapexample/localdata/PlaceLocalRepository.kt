package com.dbao1608.googlemapexample.localdata

import android.content.Context
import androidx.lifecycle.LiveData
import com.dbao1608.googlemapexample.mapper.PlaceEntityMapper
import com.dbao1608.googlemapexample.model.Place

class PlaceLocalRepository(context: Context) {
    private var placeDao: PlaceDao

    init {
        val db = CacheDatabase.getInstance(context)
        placeDao = db.placeDao()
    }

    fun insertPlaceChoose(placeEntity: PlaceEntity){
        placeDao.insertPlaceChoose(placeEntity)
    }

    fun getHistory(): LiveData<List<PlaceEntity>>{
        return placeDao.getHistoryPlaceChoose()
    }
}