package com.dbao1608.domain.entity

import com.dbao1608.domain.entity.Address

class Direction{
    var startAddress: Address? = null
    var endAddress: Address? = null
    var routes = ArrayList<List<HashMap<String, String>>>()
}



