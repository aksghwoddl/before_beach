package com.lee.domain.model.kakao

class CurrentLatLng {
    companion object{
        private lateinit var instance : CurrentLatLng
        fun getInstance() : CurrentLatLng {
            if(!::instance.isInitialized){
                instance = CurrentLatLng()
            }
            return instance
        }
    }

    private var longitude : Double = 0.0
    private var latitude : Double = 0.0
    private var wcongLongitude : Double = 0.0
    private var wcongLatitude : Double = 0.0

    fun getLongitude() = longitude
    fun setLongitude(longitude : Double){
        this.longitude = longitude
    }

    fun getLatitude() = latitude
    fun setLatitude(latitude : Double){
        this.latitude = latitude
    }

    fun getWcongLongitude() = wcongLongitude
    fun setWconLongitude(longitude: Double) {
        wcongLongitude = longitude
    }

    fun getWcongLatitude() = wcongLatitude
    fun setWcongLatitude(latitude: Double) {
        wcongLatitude = latitude
    }

}