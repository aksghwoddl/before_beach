package com.lee.data.model.kakao

import com.google.gson.annotations.SerializedName

data class WcongDTO(
    val meta : WcongMetaDTO,
    val documents : ArrayList<WcongDocumentsDTO>
)

data class WcongMetaDTO(
    @SerializedName("total_count")
    val totalCount : String
)

data class WcongDocumentsDTO(
    @SerializedName("x")
    val longitude: String ,
    @SerializedName("y")
    val latitude: String
)