package com.lee.beachcongetion.retrofit.model

data class BeachCongestionModel(
    val etlDt : String = "",
    val seqId : Int = -1,
    val poiNm : String = "",
    val uniqPop : Int = -1,
    var congestion : String = ""
)