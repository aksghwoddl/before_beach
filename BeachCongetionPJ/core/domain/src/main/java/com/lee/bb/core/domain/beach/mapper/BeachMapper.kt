package com.lee.bb.core.domain.beach.mapper

import com.lee.bb.core.data.dto.BeachDTO
import com.lee.bb.core.data.dto.BeachListDTO
import com.lee.bb.core.domain.beach.model.Beach
import com.lee.bb.core.domain.beach.model.Congestion

private fun BeachDTO.toBeach(): Beach {
    return Beach(
        name = this.poiNm,
        congestion = when (congestion) {
            "1" -> Congestion.LOW
            "2" -> Congestion.MEDIUM
            "3" -> Congestion.HIGH
            else -> {
                Congestion.LOW
            }
        }
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