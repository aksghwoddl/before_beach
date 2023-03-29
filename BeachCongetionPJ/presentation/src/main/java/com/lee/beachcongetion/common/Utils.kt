package com.lee.beachcongetion.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.IBinder
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.lee.data.common.Navi

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
        const val ACTION_MARK_BEACH_PIN = "com.lee.beachcongetion.common.Utils.ACTION_MARK_BEACH_PIN"
        const val EXTRA_SELECTED_POI = "selected_poi"
        const val KAKAO_MAP_MARKET_URI = "market://details?id=net.daum.android.map"
        const val TMAP_MARKET_URI = "market://details?id=com.skt.tmap.ku"
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

        /**
         * BottomSheet Dialog 비율 설정하는 함수
         * **/
        fun setupRatio(bottomSheetDialog: BottomSheetDialog , activity: Activity){
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View // BottomSheet를 띄우는 내부의 FrameLayout을 가져온다.
            val behavior = BottomSheetBehavior.from(bottomSheet) // BottomSheetBehavior가져오기
            val layoutParams = bottomSheet.layoutParams // 레리아웃 파라미터 가져오기
            layoutParams.height = setBottomSheetDialogHeight(activity)
            bottomSheet.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        /**
         * 기기 높이 대비 높이 설정하는 함수
         * **/
        private fun setBottomSheetDialogHeight(activity: Activity): Int {
            return getWindowHeight(activity) * 80 / 100
        }

        /**
         * 기기의 높이 구하는 함수
         * **/
        private fun getWindowHeight(activity: Activity): Int {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }

        /**
         * 키패드 숨기는 함수
         * **/
        fun hideSoftInputKeyboard(context : Context , token : IBinder?) {
            token?.let {
                val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(token  , 0)
            }
        }

        /**
         * 앱 권한 확인하는 함수
         * **/
         fun checkPermission(listener : PermissionListener) {
            val tedPermission = TedPermission.create()
            tedPermission.run {
                setPermissionListener(listener)
                setDeniedMessage("위치정보에 대한 권한이 필요합니다.")
                setPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                check()
            }
        }

        /**
         * URL을 통해 설정된 네비게이션을 확인하여 길안내 화면으로 이동하는 함수
         * - url : 전달받은 길안내 URL Scheme
         * - activity : 호출된 activity
         * - currentNavi : 현재 설정된 기본 Navi
         * **/
        fun startNavigationWithIntent(url : String , activity: Activity , currentNavi : String) {
            with(Intent(Intent.ACTION_VIEW, Uri.parse(url))){
                addCategory(Intent.CATEGORY_BROWSABLE)
                val packageManager = activity.packageManager
                val list = packageManager.queryIntentActivities(this , PackageManager.MATCH_DEFAULT_ONLY)
                if (list.isEmpty()){ // 앱이 설치되어 있지 않다면
                    when(currentNavi){
                        Navi.KAKAO_MAP.name -> {
                            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(KAKAO_MAP_MARKET_URI)))
                        }
                        Navi.TMAP.name -> {
                            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TMAP_MARKET_URI)))
                        }
                    }
                }else{ // 앱이 설치되어 있다면
                    activity.startActivity(this)
                }
            }
        }
    }
}