package com.lee.domain.model.kakao

data class Wcong(
    val meta : WcongMeta,
    val documents : ArrayList<WcongDocuments>
)

data class WcongMeta(
    val totalCount : String
)

data class WcongDocuments(
    val longitude: String ,
    val latitude: String
)