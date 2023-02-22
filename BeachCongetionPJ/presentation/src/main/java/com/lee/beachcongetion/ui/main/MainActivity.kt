package com.lee.beachcongetion.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lee.beachcongetion.BuildConfig
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.base.BaseActivity
import com.lee.beachcongetion.databinding.ActivityMainBinding
import com.lee.beachcongetion.ui.main.adapter.BeachRecyclerAdapter
import com.lee.beachcongetion.ui.main.viewmodel.MainViewModel
import com.lee.domain.model.beach.Beach
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var map : MapView
    private lateinit var beachRecyclerAdapter: BeachRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        map = MapView(this@MainActivity)
        binding.mapView.addView(map)
        binding.mainActivity = this@MainActivity
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllBeachCongestion()
    }

    private fun initRecyclerView() {
        beachRecyclerAdapter = BeachRecyclerAdapter()
        beachRecyclerAdapter.setOnItemClickListener(object : BeachRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: Beach, pos: Int) {
                val selectedBeachName = data.poiNm + "해수욕장"
                viewModel.getKakaoPoiList(BuildConfig.KAKAO_API_KEY , selectedBeachName)
            }
        })
        binding.beachRecyclerView.run {
            layoutManager = LinearLayoutManager(this@MainActivity , RecyclerView.VERTICAL, false)
            adapter = beachRecyclerAdapter
        }
    }

    /**
     * LiveData 관찰 함수
     * **/
    override fun observeData() {
        with(viewModel){
            beachList.observe(this@MainActivity) { // 해변 목록
                beachRecyclerAdapter.setList(it.mBeachList)
                beachRecyclerAdapter.notifyItemRangeChanged(0 , beachRecyclerAdapter.itemCount)
            }


            isProgress.observe(this@MainActivity){ // 진행 상태
                if(it){
                    binding.progressBar.visibility = android.view.View.VISIBLE
                } else {
                    binding.progressBar.visibility = android.view.View.GONE
                }
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

    }
}