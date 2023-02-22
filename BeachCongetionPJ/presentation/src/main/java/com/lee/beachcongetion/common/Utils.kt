package com.lee.beachcongetion.common
// For Congestion
enum class Congestion(val congest : String) {
    NORMAL("1") , FEW_CONGESTION("2") , CONGEST("3")
}

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
    }
}