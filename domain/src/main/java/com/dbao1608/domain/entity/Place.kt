package com.dbao1608.domain.entity

import com.dbao1608.domain.entity.DataType

data class Place(
    var id: String?
    , var name: String?
    , var address: String?
    , var typeData: DataType
)

