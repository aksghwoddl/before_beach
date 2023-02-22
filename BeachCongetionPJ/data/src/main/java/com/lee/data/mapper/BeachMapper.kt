package com.lee.data.mapper

import com.lee.data.model.beach.BeachDTO
import com.lee.data.model.beach.BeachListDTO
import com.lee.domain.model.beach.Beach
import com.lee.domain.model.beach.BeachList

object BeachMapper {
    private fun mapperToBeach(beachDTO: BeachDTO) : Beach {
        val beach = beachDTO.run {
            Beach(poiNm, congestion)
        }
        return beach
    }

    fun mapperToBeachList(beachListDTO: BeachListDTO) : BeachList {
        val list = arrayListOf<Beach>()
        beachListDTO.getAllBeachList().forEach {
            val beach = mapperToBeach(it)
            list.add(beach)
        }
        return BeachList(list)
    }
}