package com.lee.domain.model.kakao

import androidx.annotation.Keep

@Keep
data class Wcong(
    val meta : WcongMeta,
    val documents : ArrayList<WcongDocuments>
)

@Keep
data class WcongMeta(
    val totalCount : String
)

@Keep
data class WcongDocuments(
    val longitude: String ,
    val latitude: String
)