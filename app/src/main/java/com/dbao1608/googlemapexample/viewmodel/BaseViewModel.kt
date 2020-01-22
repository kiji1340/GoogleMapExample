package com.dbao1608.googlemapexample.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

open class BaseViewModel() : ViewModel() {
    protected var lifecycleOwner: LifecycleOwner? = null
    protected var context: Context? = null

    fun attach(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    fun isAttached(): LifecycleOwner? {
        return lifecycleOwner
    }

    open fun addContext(context: Context){
        this.context = context

    }
}