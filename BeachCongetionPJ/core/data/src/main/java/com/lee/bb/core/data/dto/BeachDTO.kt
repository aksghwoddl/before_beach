package com.lee.bb.core.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class BeachDTO(
    val etlDt: String = "",
    val seqId: Int = -1,
    val poiNm: String = "",
    val uniqPop: Int = -1,
    var congestion: String = ""
)