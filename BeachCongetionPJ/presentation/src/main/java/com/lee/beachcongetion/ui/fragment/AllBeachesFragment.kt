package com.lee.beachcongetion.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lee.beachcongetion.R
import com.lee.beachcongetion.data.retrofit.model.beach.BeachCongestionModel
import com.lee.beachcongetion.databinding.FragmentAllBeachesBinding
import com.lee.beachcongetion.ui.adapter.BeachRecyclerAdapter
import com.lee.beachcongetion.ui.factory.BeachViewModelFactory
import com.lee.beachcongetion.ui.viewmodel.BeachViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class AllBeachesFragment : Fragment() {
    private val TAG = "AllBeachesFragment"
    private lateinit var binding : FragmentAllBeachesBinding

    private lateinit var mBeachRecyclerAdapter: BeachRecyclerAdapter
    private lateinit var mBeachViewModel : BeachViewModel
    private lateinit var mMap : MapView

    companion object{
        fun newInstance() = AllBeachesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllBeachesBinding.inflate(inflater , container , false)
        mMap = MapView(context)
        binding.mapView.addView(mMap)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initBeachViewModel()
    }

    override fun onResume() {
        super.onResume()
        mBeachViewModel.getAllBeachCongestion()
    }

    /**
     * Function for init viewModel
     * **/

    private fun initBeachViewModel(){
        mBeachViewModel = ViewModelProvider(this , BeachViewModelFactory())[BeachViewModel::class.java]

        // RecyclerView list observing
        mBeachViewModel.beachList.observe(viewLifecycleOwner , Observer {
           mBeachRecyclerAdapter.setList(it)
           mBeachRecyclerAdapter.notifyItemRangeChanged(0 , mBeachRecyclerAdapter.itemCount)
        })

        // ProgressBar observing
        mBeachViewModel.progressVisible.observe(viewLifecycleOwner){
            if(it){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        // For toast message when occur error
        mBeachViewModel.errorMessage.observe(viewLifecycleOwner , Observer {
            Toast.makeText(context , "서버에서 정상적으로 정보를 가져오지 못했습니다. : $it", Toast.LENGTH_SHORT).show()
        })

        // For Kakao Poi List
        mBeachViewModel.poiList.observe(viewLifecycleOwner){
            mMap.removeAllPOIItems()
            val poi = it[0]

            // Convert WNG84 to WCONG because kakao map use WCONG coordinate system
            // mapPointWithGeoCoord was not work so I convert coordinate by using api

            CoroutineScope(Dispatchers.IO).launch{
                val wcongModel = mBeachViewModel.getWcongPoint(resources.getString(R.string.kakao_api_key ), poi.longitude , poi.latitude)?.documents
                wcongModel?.let {
                   val longitude = it[0].longitude.toDouble()
                   val latitude = it[0].latitude.toDouble()
                    CoroutineScope(Dispatchers.Main).launch{
                        val marker = MapPOIItem()
                        with(marker){
                            itemName = poi.placeName
                            tag = 0
                            mapPoint = MapPoint.mapPointWithWCONGCoord(longitude , latitude)
                            markerType = MapPOIItem.MarkerType.BluePin
                            selectedMarkerType = MapPOIItem.MarkerType.RedPin
                        }
                        mMap.addPOIItem(marker)
                        mMap.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithWCONGCoord(longitude , latitude)
                            , 4
                            ,true)
                    }
                }?:let {
                    mBeachViewModel.errorMessage.postValue("좌표를 변환중 문제가 발생했습니다.")
                }
            }
        }
    }

    /**
     * Function that initialize RecyclerView
     * **/
    private fun initRecyclerView() {
        mBeachRecyclerAdapter = BeachRecyclerAdapter()
        with(binding) {
            beachRecyclerView.layoutManager = LinearLayoutManager(context , RecyclerView.VERTICAL, false)
            mBeachRecyclerAdapter.setOnItemClickListener(object : BeachRecyclerAdapter.OnItemClickListener{
                override fun onItemClick(v: View, data: BeachCongestionModel, pos: Int) {
                    val selectedBeachName = data.poiNm + "해수욕장"
                    mBeachViewModel.getKakaoPoiList(resources.getString(R.string.kakao_api_key) , selectedBeachName)
                }
            })
            beachRecyclerView.adapter = mBeachRecyclerAdapter
        }
    }
}