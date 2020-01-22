package com.dbao1608.googlemapexample.localdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_table")
class PlaceEntity {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var address: String = ""
    var time: Long = 0
}