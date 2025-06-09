package com.lee.bb.core.domain.beach.mapper

import com.lee.bb.core.data.dto.BeachDTO
import com.lee.bb.core.data.dto.BeachListDTO
import com.lee.bb.core.domain.beach.model.Beach

private fun BeachDTO.toBeach(): Beach {
    return Beach(
        name = this.poiNm,
        congestion = this.congestion
    )
}

internal fun BeachListDTO.toBeachList(): List<Beach> {
    return buildList {
        this@toBeachList.allBeachList.forEach { beach ->
            beach?.let {
                add(it.toBeach())
            }
        }
    }
}