package com.lee.data.model.kakao

import com.google.gson.annotations.SerializedName

data class KaKaoPoiDTO(
    val documents : MutableList<DocumentsDTO>,
    val meta : MetaDTO
)

data class DocumentsDTO(
    @SerializedName("address_name")
    val addressName : String ,
    @SerializedName("category_group_code")
    val categoryGroupCode : String ,
    @SerializedName("category_group_name")
    val categoryGroupName : String ,
    @SerializedName("category_name")
    val categoryName : String ,
    val distance : String ,
    val id : String ,
    val phone : String ,
    @SerializedName("place_name")
    val placeName : String ,
    @SerializedName("place_url")
    val placeURL : String ,
    @SerializedName("road_address_name")
    val roadAddressName : String ,
    @SerializedName("x")
    val longitude : String ,
    @SerializedName("y")
    val latitude : String
)

data class MetaDTO(
    @SerializedName("is_end")
    val isEnd : Boolean,
    @SerializedName("pageable_count")
    val pageableCount : Int,
    @SerializedName("same_name")
    val sameName : SameNameDTO,
    @SerializedName("total_count")
    val totalCount : Int
)

data class SameNameDTO(
    val keyword : String ,
    val region : MutableList<String> ,
    val selectedRegion : String?
)

