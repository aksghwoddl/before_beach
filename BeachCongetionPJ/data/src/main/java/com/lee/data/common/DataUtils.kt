package com.lee.data.common

class DataUtils {
    companion object{
        const val GET_BEACH_CONGESTION_URL = "/seantour_map/travel/getBeachCongestionApi.do"
        const val KAKAO_POI_URL = "v2/local/search/keyword.json"
        const val KAKAO_CONVERT_WCONG_URL = "v2/local/geo/transcoord.json?&output_coord=WCONGNAMUL"
        const val AUTHORIZATION = "Authorization"

        const val CURRENT_NAVI = "current_navi"
        const val SETTINGS = "settings"
    }
}