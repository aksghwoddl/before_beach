package com.lee.domain.model.beach

/**
 * Data로 부터 전달받은 해변 정보
 * **/
data class Beach(
    val poiNm : String = "",
    var congestion : String = ""
)