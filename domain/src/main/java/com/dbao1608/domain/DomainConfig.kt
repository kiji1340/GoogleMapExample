package com.dbao1608.domain

import android.content.Context
import com.dbao1608.remotedata.ApiClient

class DomainConfig{
    fun initialize(context: Context){
        ApiClient.key = context.getString(R.string.google_maps_key)
    }
}