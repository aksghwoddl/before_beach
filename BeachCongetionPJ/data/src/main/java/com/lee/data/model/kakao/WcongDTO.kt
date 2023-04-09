package com.lee.data.model.kakao

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WcongDTO(
    val meta : WcongMetaDTO,
    val documents : ArrayList<WcongDocumentsDTO>
)

@Keep
data class WcongMetaDTO(
    @SerializedName("total_count")
    val totalCount : String
)

@Keep
data class WcongDocumentsDTO(
    @SerializedName("x")
    val longitude: String ,
    @SerializedName("y")
    val latitude: String
)