package com.dbao1608.domain.mapper

interface Mapper<I,O>{
    fun transformer(type: I): O
    interface Extend<C, O>{
        fun transformerFromCache(type: C): O
    }
}