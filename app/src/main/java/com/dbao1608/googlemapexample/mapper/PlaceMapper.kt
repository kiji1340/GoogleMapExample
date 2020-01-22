package com.dbao1608.googlemapexample.mapper

import com.dbao1608.googlemapexample.localdata.PlaceEntity
import com.dbao1608.googlemapexample.model.DataType
import com.dbao1608.googlemapexample.model.Place
import com.dbao1608.googlemapexample.remotedata.reponse.PlaceResponse

class PlaceMapper : Mapper<PlaceResponse, List<Place>>, Mapper.Extend<PlaceEntity, Place> {
    companion object {
        private var mapper: PlaceMapper? = null
        fun getInstance(): PlaceMapper {
            if (mapper == null) mapper = PlaceMapper()
            return mapper!!
        }

    }

    override fun transformer(type: PlaceResponse): List<Place> {
        val list = ArrayList<Place>()
        for (candidate in type.candidates) {
            val place = Place(
                id = candidate.place_id
                , name = candidate.name
                , typeData = DataType.REMOTE
                , address = candidate.formatted_address
            )

            list.add(place)
        }
        return list
    }

    override fun transformerFromCache(type: PlaceEntity): Place {
        return Place(
            id = type.id
            , name = type.name
            , address = type.address
            , typeData = DataType.LOCAL
        )
    }

}

class PlaceEntityMapper : Mapper<Place, PlaceEntity> {
    override fun transformer(type: Place): PlaceEntity {
        return PlaceEntity().apply {
            type.id?.let {
                id = it
            }
            type.address?.let {
                address = it
            }

            type.name?.let {
                name = it
            }

            time = System.currentTimeMillis()
        }
    }

}