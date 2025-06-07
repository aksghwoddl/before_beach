package com.lee.domain.model.kakao

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class KaKaoPoi(
    val documents : ArrayList<Documents>,
    val meta : Meta
) : Serializable

@Keep
data class Documents(
    val addressName : String ,
    val categoryGroupCode : String ,
    val categoryGroupName : String ,
    val categoryName : String ,
    val distance : String ,
    val id : String ,
    val phone : String ,
    val placeName : String ,
    val placeURL : String ,
    val roadAddressName : String ,
    var longitude : String ,
    var latitude : String
) : Serializable

@Keep
data class Meta(
    val isEnd : Boolean,
    val pageableCount : Int,
    val sameName : SameName,
    val totalCount : Int
) : Serializable

@Keep
data class SameName(
    val keyword : String ,
    val region : MutableList<String> ,
    val selectedRegion : String?
) : Serializable