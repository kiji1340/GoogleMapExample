package com.dbao1608.localdata.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.dbao1608.localdata.abstraction.repository.PlaceRepository
import com.dbao1608.localdata.dao.PlaceDao
import com.dbao1608.localdata.database.CacheDatabase
import com.dbao1608.localdata.entity.PlaceEntity

class PlaceLocalRepository(context: Context): PlaceRepository {
    private var placeDao: PlaceDao

    init {
        val db = CacheDatabase.getInstance(context)
        placeDao = db.placeDao()
    }

    override fun insertPlace(placeEntity: PlaceEntity) {
        insert(placeEntity)
    }

    override fun getPlace(): LiveData<List<PlaceEntity>> = getListPlace()

    private fun insert(placeEntity: PlaceEntity){
        placeDao.insertPlaceChoose(placeEntity)
    }

    private fun getListPlace(): LiveData<List<PlaceEntity>>{
        return placeDao.getHistoryPlaceChoose()
    }
}