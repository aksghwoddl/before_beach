package com.lee.data.model.beach

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Beach Congestion List
 * it has all beaches list and each beach's key
 * **/

@Keep
class BeachListDTO {
    private lateinit var mBeachList : MutableList<BeachDTO>

    @SerializedName("Beach0")
    val beach0 : BeachDTO? = null
    @SerializedName("Beach1")
    val beach1 : BeachDTO? = null
    @SerializedName("Beach2")
    val beach2 : BeachDTO? = null
    @SerializedName("Beach3")
    val beach3 : BeachDTO? = null
    @SerializedName("Beach4")
    val beach4 : BeachDTO? = null
    @SerializedName("Beach5")
    val beach5 : BeachDTO? = null
    @SerializedName("Beach6")
    val beach6 : BeachDTO? = null
    @SerializedName("Beach7")
    val beach7 : BeachDTO? = null
    @SerializedName("Beach8")
    val beach8 : BeachDTO? = null
    @SerializedName("Beach9")
    val beach9 : BeachDTO? = null
    @SerializedName("Beach10")
    val beach10 : BeachDTO? = null
    @SerializedName("Beach11")
    val beach11 : BeachDTO? = null
    @SerializedName("Beach12")
    val beach12 : BeachDTO? = null
    @SerializedName("Beach13")
    val beach13 : BeachDTO? = null
    @SerializedName("Beach14")
    val beach14 : BeachDTO? = null
    @SerializedName("Beach15")
    val beach15 : BeachDTO? = null
    @SerializedName("Beach16")
    val beach16 : BeachDTO? = null
    @SerializedName("Beach17")
    val beach17 : BeachDTO? = null
    @SerializedName("Beach18")
    val beach18 : BeachDTO? = null
    @SerializedName("Beach19")
    val beach19 : BeachDTO? = null
    @SerializedName("Beach20")
    val beach20 : BeachDTO? = null
    @SerializedName("Beach21")
    val beach21 : BeachDTO? = null
    @SerializedName("Beach22")
    val beach22 : BeachDTO? = null
    @SerializedName("Beach23")
    val beach23 : BeachDTO? = null
    @SerializedName("Beach24")
    val beach24 : BeachDTO? = null
    @SerializedName("Beach25")
    val beach25 : BeachDTO? = null
    @SerializedName("Beach26")
    val beach26 : BeachDTO? = null
    @SerializedName("Beach27")
    val beach27 : BeachDTO? = null
    @SerializedName("Beach28")
    val beach28 : BeachDTO? = null
    @SerializedName("Beach29")
    val beach29 : BeachDTO? = null
    @SerializedName("Beach30")
    val beach30 : BeachDTO? = null
    @SerializedName("Beach31")
    val beach31 : BeachDTO? = null
    @SerializedName("Beach32")
    val beach32 : BeachDTO? = null
    @SerializedName("Beach33")
    val beach33 : BeachDTO? = null
    @SerializedName("Beach34")
    val beach34 : BeachDTO? = null
    @SerializedName("Beach35")
    val beach35 : BeachDTO? = null
    @SerializedName("Beach36")
    val beach36 : BeachDTO? = null
    @SerializedName("Beach37")
    val beach37 : BeachDTO? = null
    @SerializedName("Beach38")
    val beach38 : BeachDTO? = null
    @SerializedName("Beach39")
    val beach39 : BeachDTO? = null
    @SerializedName("Beach40")
    val beach40 : BeachDTO? = null
    @SerializedName("Beach41")
    val beach41 : BeachDTO? = null
    @SerializedName("Beach42")
    val beach42 : BeachDTO? = null
    @SerializedName("Beach43")
    val beach43 : BeachDTO? = null
    @SerializedName("Beach44")
    val beach44 : BeachDTO? = null
    @SerializedName("Beach45")
    val beach45 : BeachDTO? = null
    @SerializedName("Beach46")
    val beach46 : BeachDTO? = null
    @SerializedName("Beach47")
    val beach47 : BeachDTO? = null
    @SerializedName("Beach48")
    val beach48 : BeachDTO? = null
    @SerializedName("Beach49")
    val beach49 : BeachDTO? = null

    fun getAllBeachList() : MutableList<BeachDTO> {
        if(!::mBeachList.isInitialized){
            mBeachList = mutableListOf()
            mBeachList.add(beach0 as BeachDTO)
            mBeachList.add(beach1 as BeachDTO)
            mBeachList.add(beach2 as BeachDTO)
            mBeachList.add(beach3 as BeachDTO)
            mBeachList.add(beach4 as BeachDTO)
            mBeachList.add(beach5 as BeachDTO)
            mBeachList.add(beach6 as BeachDTO)
            mBeachList.add(beach7 as BeachDTO)
            mBeachList.add(beach8 as BeachDTO)
            mBeachList.add(beach9 as BeachDTO)
            mBeachList.add(beach10 as BeachDTO)
            mBeachList.add(beach11 as BeachDTO)
            mBeachList.add(beach12 as BeachDTO)
            mBeachList.add(beach13 as BeachDTO)
            mBeachList.add(beach14 as BeachDTO)
            mBeachList.add(beach15 as BeachDTO)
            mBeachList.add(beach16 as BeachDTO)
            mBeachList.add(beach17 as BeachDTO)
            mBeachList.add(beach18 as BeachDTO)
            mBeachList.add(beach19 as BeachDTO)
            mBeachList.add(beach20 as BeachDTO)
            mBeachList.add(beach21 as BeachDTO)
            mBeachList.add(beach22 as BeachDTO)
            mBeachList.add(beach23 as BeachDTO)
            mBeachList.add(beach24 as BeachDTO)
            mBeachList.add(beach25 as BeachDTO)
            mBeachList.add(beach26 as BeachDTO)
            mBeachList.add(beach27 as BeachDTO)
            mBeachList.add(beach28 as BeachDTO)
            mBeachList.add(beach29 as BeachDTO)
            mBeachList.add(beach30 as BeachDTO)
            mBeachList.add(beach31 as BeachDTO)
            mBeachList.add(beach32 as BeachDTO)
            mBeachList.add(beach33 as BeachDTO)
            mBeachList.add(beach34 as BeachDTO)
            mBeachList.add(beach35 as BeachDTO)
            mBeachList.add(beach36 as BeachDTO)
            mBeachList.add(beach37 as BeachDTO)
            mBeachList.add(beach38 as BeachDTO)
            mBeachList.add(beach39 as BeachDTO)
            mBeachList.add(beach40 as BeachDTO)
            mBeachList.add(beach41 as BeachDTO)
            mBeachList.add(beach42 as BeachDTO)
            mBeachList.add(beach43 as BeachDTO)
            mBeachList.add(beach44 as BeachDTO)
            mBeachList.add(beach45 as BeachDTO)
            mBeachList.add(beach46 as BeachDTO)
            mBeachList.add(beach47 as BeachDTO)
            mBeachList.add(beach48 as BeachDTO)
            mBeachList.add(beach49 as BeachDTO)
        }
       return mBeachList
    }
}