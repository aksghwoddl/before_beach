package com.lee.bb.domain.beach.mapper

import com.lee.bb.data.dto.BeachDTO
import com.lee.bb.data.dto.BeachListDTO
import com.lee.bb.domain.beach.model.Beach

private fun BeachDTO.toBeach(): Beach {
    return Beach(
        name = this.poiNm,
        congestion = this.congestion
    )
}

internal fun BeachListDTO.toBeachList(): List<Beach> {
    return buildList {
        this@toBeachList.allBeachList.forEach {
            add(it.toBeach())
        }
    }
}