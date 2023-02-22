package com.lee.beachcongetion.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lee.beachcongetion.BuildConfig
import com.lee.beachcongetion.R
import com.lee.beachcongetion.databinding.FragmentAllBeachesBinding
import com.lee.beachcongetion.ui.adapter.BeachRecyclerAdapter
import com.lee.beachcongetion.ui.viewmodel.BeachViewModel
import com.lee.domain.model.beach.Beach
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class AllBeachesFragment : Fragment() {
    private lateinit var binding : FragmentAllBeachesBinding

    private lateinit var beachRecyclerAdapter: BeachRecyclerAdapter
    private val viewModel : BeachViewModel by viewModels()
    private lateinit var map : MapView

    companion object{
        fun newInstance() = AllBeachesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllBeachesBinding.inflate(inflater , container , false)
        map = MapView(requireActivity())
        binding.mapView.addView(map)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initBeachViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllBeachCongestion()
    }

    private fun initBeachViewModel(){
        with(viewModel){
            beachList.observe(viewLifecycleOwner) { // 해변 목록
                beachRecyclerAdapter.setList(it.mBeachList)
                beachRecyclerAdapter.notifyItemRangeChanged(0 , beachRecyclerAdapter.itemCount)
            }


            isProgress.observe(viewLifecycleOwner){ // 진행 상태
                if(it){
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }

            toastMessage.observe(viewLifecycleOwner){ // Toast Message
                Toast.makeText(requireContext() , it , Toast.LENGTH_SHORT).show()
            }

            poiList.observe(viewLifecycleOwner){ // 검색된 좌표
                map.removeAllPOIItems()
                val poi = it[0]
                getWcongPoint(BuildConfig.KAKAO_API_KEY , poi.longitude , poi.latitude)  // WNG84좌표를 WCONG 좌표로 변경
            }

            wcongList.observe(viewLifecycleOwner){ // 변환된 좌표
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
                    setMapCenterPointAndZoomLevel(MapPoint.mapPointWithWCONGCoord(longitude , latitude) // 지도 확대
                        , 4
                        ,true)
                }
            }
        }
    }

    private fun initRecyclerView() {
        beachRecyclerAdapter = BeachRecyclerAdapter()
        with(binding) {
            beachRecyclerView.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL, false)
            beachRecyclerAdapter.setOnItemClickListener(object : BeachRecyclerAdapter.OnItemClickListener{
                override fun onItemClick(v: View, data: Beach , pos: Int) {
                    val selectedBeachName = data.poiNm + "해수욕장"
                    viewModel.getKakaoPoiList(BuildConfig.KAKAO_API_KEY , selectedBeachName)
                }
            })
            beachRecyclerView.adapter = beachRecyclerAdapter
        }
    }
}