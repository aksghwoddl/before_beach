package com.lee.beachcongetion.ui.activity.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lee.beachcongetion.BuildConfig
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.Utils
import com.lee.beachcongetion.common.base.BaseActivity
import com.lee.beachcongetion.databinding.ActivityMainBinding
import com.lee.beachcongetion.ui.fragment.BeachListBottomSheetDialogFragment
import com.lee.beachcongetion.ui.activity.main.viewmodel.MainViewModel
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

            }

            toastMessage.observe(this@MainActivity){ // Toast Message
                android.widget.Toast.makeText(this@MainActivity , it , android.widget.Toast.LENGTH_SHORT).show()
            }

            poiList.observe(this@MainActivity){ // 검색된 좌표
                map.removeAllPOIItems()
                val poi = it[0]
                getWcongPoint(BuildConfig.KAKAO_API_KEY , poi.longitude , poi.latitude)  // WNG84좌표를 WCONG 좌표로 변경
            }

            wcongList.observe(this@MainActivity){ // 변환된 좌표
                val longitude = it[0].longitude.toDouble()
                val latitude = it[0].latitude.toDouble()
                val marker = MapPOIItem()
                with(marker){
                    itemName = poiList.value?.get(0)?.placeName
                    tag = 0
                    mapPoint = MapPoint.mapPointWithWCONGCoord(longitude , latitude) // 좌표 찍기
                    markerType = MapPOIItem.MarkerType.BluePin // 선택되지 않은 마커 타입
                    selectedMarkerType = MapPOIItem.MarkerType.RedPin // 선택된 마커 타입
                }
                map.run { // 지도에 마커 찍기
                    addPOIItem(marker)
                    setMapCenterPointAndZoomLevel(
                        MapPoint.mapPointWithWCONGCoord(longitude , latitude) // 지도 확대
                        , 4
                        ,true)
                }
            }
        }
    }

    /**
     * Listener 등록 함수
     * **/
    override fun addListeners() {
        with(binding){
            showListLayout.setOnClickListener {
                val beachListFragment = BeachListBottomSheetDialogFragment.newInstance()
                beachListFragment.show(supportFragmentManager , TAG)
            }
        }
    }

    private fun initBroadcastReceiver() {
        mainActivityReceiver = MainActivityReceiver(viewModel)
        val intentFilter = IntentFilter()
        intentFilter.addAction(Utils.ACTION_MARK_BEACH_PIN)
        registerReceiver(mainActivityReceiver , intentFilter)
    }

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