package com.lee.data.model.beach

data class BeachDTO(
    val etlDt : String = "",
    val seqId : Int = -1,
    val poiNm : String = "",
    val uniqPop : Int = -1,
    var congestion : String = ""
)