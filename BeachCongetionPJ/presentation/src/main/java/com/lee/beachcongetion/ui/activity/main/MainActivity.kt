package com.lee.beachcongetion.ui.activity.main

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lee.beachcongetion.BuildConfig
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.Utils
import com.lee.beachcongetion.common.base.BaseActivity
import com.lee.beachcongetion.databinding.ActivityMainBinding
import com.lee.beachcongetion.ui.activity.main.viewmodel.MainViewModel
import com.lee.beachcongetion.ui.fragment.list.BeachListBottomSheetDialogFragment
import com.lee.beachcongetion.ui.fragment.search.SearchBottomSheetDialogFragment
import com.lee.domain.model.kakao.CurrentLatLng
import com.lee.domain.model.kakao.KaKaoPoi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var mainActivityReceiver: MainActivityReceiver
    private lateinit var map : MapView
    private lateinit var currentLocationMarker : MapPOIItem // 현재위치 마커

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        map = MapView(this@MainActivity)
        lifecycleScope.launch{
            withContext(Dispatchers.Default){
                binding.mapView.addView(map)
            }
        }
        binding.mainActivity = this@MainActivity
        initBroadcastReceiver()
        viewModel.getAllBeachCongestion()
    }

    override fun onStart() {
        super.onStart()
        if(!::currentLocationMarker.isInitialized){ // 현재위치가 아직 설정되지 않은 최초의 상태일때만 화면이 올라올때 현재위치를 불러온다.
            getCurrentLocation() // 앱이 시작되면 현재위치로 지도를 이동시킨다.
        }
    }

    override fun onDestroy() {
        unregisterReceiver(mainActivityReceiver)
        super.onDestroy()
    }

    /**
     * LiveData 관찰 함수
     * **/
    override fun observeData() {
        with(viewModel){
            isProgress.observe(this@MainActivity){ // 진행 상태
                if(it){
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }

            toastMessage.observe(this@MainActivity){ // Toast Message
                android.widget.Toast.makeText(this@MainActivity , it , android.widget.Toast.LENGTH_SHORT).show()
            }

            poiList.observe(this@MainActivity){ poiList -> // 검색된 좌표
                map.removeAllPOIItems()
                val makers = arrayListOf<MapPOIItem>()
                lifecycleScope.launch(Dispatchers.Default) {
                    poiList.forEach {
                        val wcong = getWcongPoint(BuildConfig.KAKAO_API_KEY , it.longitude , it.latitude)  // WNG84좌표를 WCONG 좌표로 변경
                        it.longitude = wcong.documents[0].longitude
                        it.latitude = wcong.documents[0].latitude

                        val marker = MapPOIItem()
                        val searchMapPoint = MapPoint.mapPointWithWCONGCoord(it.longitude.toDouble() , it.latitude.toDouble())
                        marker.run{
                            itemName = it.placeName
                            mapPoint = searchMapPoint
                            markerType = MapPOIItem.MarkerType.BluePin // 선택되지 않은 마커 타입
                            selectedMarkerType = MapPOIItem.MarkerType.RedPin // 선택된 마커 타입
                        }
                        makers.add(marker)
                    }
                    setMarker(2 , makers)
                }
            }

            currentLocation.observe(this@MainActivity){ // 현재위치
                changeCurrentLatLng(it)
            }

            currentLatLng.observe(this@MainActivity){ // 현재 위치의 좌표객체
                setCurrentLocationMarker(it)
            }
        }
    }

    /**
     * Listener 등록 함수
     * **/
    override fun addListeners() {
        with(binding){
            searchTextView.setOnClickListener { // 검색하기
                val searchFragment = SearchBottomSheetDialogFragment.newInstance(viewModel.beachList.value!!)
                searchFragment.show(supportFragmentManager , TAG)
            }

            showListLayout.setOnClickListener { // 목록 보기 버튼
                val beachListFragment = BeachListBottomSheetDialogFragment .newInstance(viewModel.beachList.value!!)
                beachListFragment.show(supportFragmentManager , TAG)
            }

            currentLocationButton.setOnClickListener { // 현재위치 버튼
                getCurrentLocation()
            }
        }
    }

    private fun initBroadcastReceiver() {
        mainActivityReceiver = MainActivityReceiver(viewModel)
        val intentFilter = IntentFilter()
        intentFilter.addAction(Utils.ACTION_MARK_BEACH_PIN)
        registerReceiver(mainActivityReceiver , intentFilter)
    }

    /**
     * 현재 위치를 가져오는 함수
     * **/
    @SuppressLint("MissingPermission") // 앱 시작시 이미 권한 체크를 끝냄
    private fun getCurrentLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        val gpsProvider = LocationManager.GPS_PROVIDER
        val networkGpsProvider = LocationManager.NETWORK_PROVIDER
        val gpsLocation = locationManager?.getLastKnownLocation(gpsProvider)
        val networkLocation = locationManager?.getLastKnownLocation(networkGpsProvider)
        with(viewModel){
            gpsLocation?.let { // GPS를 통해 현재위치를 정상적으로 받아왔을때
                setCurrentLocation(it)
            }?:let { // 현재위치를 받아오지 못했을때
                networkLocation?.let { // 네트워크 Provider를 통해 위치 받음
                   setCurrentLocation(it)
                }?: setToastMessage(getString(R.string.fail_find_current_location)) // 둘 다 실패할 경우 toast message 띄움
            }
        }
    }

    /**
     * 지도에 marker를 setting하는 함수 (한가지의 마커만 사용할때)
     * **/
    private fun setMarker(zoom : Int , marker : MapPOIItem , mapPoint: MapPoint){
        map.run { // 지도에 마커 찍기
            addPOIItem(marker)
            setMapCenterPointAndZoomLevel(
                mapPoint
                , zoom
                ,true)
        }
    }

    /**
     * 지도에 marker를 setting하는 함수 (여러 마커를 한번에 표시할때)
     * **/
    private fun setMarker(zoom : Int , makers : ArrayList<MapPOIItem>){
        map.run { // 지도에 마커 찍기
            makers.forEach {
                addPOIItem(it)
            }
            setMapCenterPointAndZoomLevel(
                makers[0].mapPoint
                , zoom
                ,true)
        }
    }

    /**
     * 현재위치 마커를 setting하는 함수
     * **/
    private fun setCurrentLocationMarker(currentLocation : CurrentLatLng) {
        val searchMapPoint = MapPoint.mapPointWithWCONGCoord(currentLocation.getWcongLongitude() , currentLocation.getWcongLatitude())
        if(!::currentLocationMarker.isInitialized){
            currentLocationMarker = MapPOIItem()
            currentLocationMarker.run{
                itemName = getString(R.string.current_location)
                markerType = MapPOIItem.MarkerType.CustomImage // 선택되지 않은 마커 타입
                customImageResourceId = R.mipmap.ic_launcher_foreground // 커스텀 마커 이미지
                isCustomImageAutoscale = false // Android 시스템에 따른 자동 scale 막기
                isShowCalloutBalloonOnTouch = false // 커스텀 마커는 말풍선 보이기 안함
                setCustomImageAnchor(0.5f , 0.5f)
            }
        }

        map.removePOIItem(currentLocationMarker) // 이전 현재위치 마커는 삭제
        currentLocationMarker.mapPoint = searchMapPoint
        setMarker(0 , currentLocationMarker , searchMapPoint)
    }

    /**
     * 현재 위치의 좌표값 객체를 변경하는 함수
     * **/
    private fun changeCurrentLatLng(currentLocation: Location) {
        lifecycleScope.launch{
            val wcong = viewModel.getWcongPoint(BuildConfig.KAKAO_API_KEY , currentLocation.longitude.toString() , currentLocation.latitude.toString())
            val searchPoint = wcong.documents[0]
            val currentLatLng = CurrentLatLng.getInstance()
            currentLatLng.run {
                setLongitude(currentLocation.longitude)
                setLatitude(currentLocation.latitude)
                setWconLongitude(searchPoint.longitude.toDouble())
                setWcongLatitude(searchPoint.latitude.toDouble())
            }
            viewModel.setCurrentLatLng(currentLatLng)
        }
    }

    /**
     * MainActivity에서 사용할 BroadcastReceiver
     * **/
    private class MainActivityReceiver(private val viewModel : MainViewModel) : BroadcastReceiver() {
        override fun onReceive(context : Context?, intent : Intent?) {
            when(intent?.action){
                Utils.ACTION_MARK_BEACH_PIN -> {
                    Log.d(TAG, "onReceive: ACTION_MARK_BEACH_PIN ")
                    val poi : KaKaoPoi? = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                        intent.getSerializableExtra(Utils.EXTRA_SELECTED_POI , KaKaoPoi::class.java)
                    } else {
                        intent.getSerializableExtra(Utils.EXTRA_SELECTED_POI) as KaKaoPoi
                    }
                    poi?.let {
                        viewModel.setPoiList(it.documents)
                    }
                }
            }
        }
    }
}