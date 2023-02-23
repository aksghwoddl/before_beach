package com.lee.beachcongetion.common

import android.content.Context
import android.net.ConnectivityManager

/**
 * 혼잡도 관리를 위한 Enum class
 * **/
enum class Congestion(val congest : String) {
    NORMAL("1") , FEW_CONGESTION("2") , CONGEST("3")
}

/**
 * 본앱에서 common하게 사용하는 Util들을 모아놓은 class
 * **/
class Utils {
    companion object{
        /**
         *  Kakao Map HashKey 가져오는 함수
         * **/
        /*private fun getHashKey() {
            var packageInfo : PackageInfo = PackageInfo()
            try {
                packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            } catch (e: PackageManager.NameNotFoundException){
                e.printStackTrace()
            }

            for (signature: Signature in packageInfo.signatures){
                try{
                    var md: MessageDigest = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    Log.e("KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))
                } catch(e: NoSuchAlgorithmException){
                    Log.e("KEY_HASH", "Unable to get MessageDigest. signature = " + signature, e)
                }
            }
        }*/

        /**
         * 네트워크 상태 체크하는 함수
         * **/
        fun checkNetworkConnection(context : Context) : Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetwork
            return networkInfo.toString() != "null"
        }
    }
}