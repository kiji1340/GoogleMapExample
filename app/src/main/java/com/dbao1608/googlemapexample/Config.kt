package com.dbao1608.googlemapexample

import android.content.Context
import com.dbao1608.googlemapexample.remotedata.reponse.DirectionResponse

class Config(private val context: Context) {
    companion object{
        private var instance : Config? = null

        fun initialize(context: Context): Config{
            if(instance == null){
                synchronized(this){
                    instance = Config(context)
                }
            }
            return instance!!
        }

        fun getInstance(): Config {
            return instance!!
        }
    }


    private var apiGoogle: String? = null

    fun getGoogleMapKey(): String{
        if(apiGoogle.isNullOrEmpty()){
            apiGoogle = context.getString(R.string.google_maps_key)
        }
        return apiGoogle!!
    }
}