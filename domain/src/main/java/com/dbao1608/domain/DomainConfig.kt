package com.dbao1608.domain

import android.content.Context
import com.dbao1608.remotedata.ApiClient

class DomainConfig{
    companion object{
        fun setKeyApi(key: String){
            ApiClient.key = key
        }
    }
}