package com.dbao1608.googlemapexample.localdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaceChoose(placeEntity: PlaceEntity)

    @Query("select * from place_table order by time desc")
    fun getHistoryPlaceChoose(): LiveData<List<PlaceEntity>>
}