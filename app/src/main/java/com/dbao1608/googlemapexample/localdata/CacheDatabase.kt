package com.dbao1608.googlemapexample.localdata

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper


@Database(entities = [PlaceEntity::class], version = 1, exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {

    companion object {
        var db: CacheDatabase? = null

        fun getInstance(context: Context): CacheDatabase {
            if(db == null){
                synchronized(this){
                    db = Room
                        .databaseBuilder(context.applicationContext
                            , CacheDatabase::class.java
                            ,"db")
                        .build()
                }
            }
            return db!!
        }
    }

    abstract fun placeDao():PlaceDao

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllTables() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}