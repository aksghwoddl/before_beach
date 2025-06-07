package com.lee.domain.model.beach

import androidx.annotation.Keep

/**
 * Data로 부터 전달받은 해변 목록 class
 * **/
@Keep
data class BeachList (
    val mBeachList: ArrayList<Beach>
)