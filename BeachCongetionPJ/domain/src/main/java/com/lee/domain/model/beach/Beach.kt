package com.lee.domain.model.beach

import androidx.annotation.Keep

/**
 * Data로 부터 전달받은 해변 정보
 * **/
@Keep
data class Beach(
    val poiNm : String = "",
    var congestion : String = ""
)