package com.lee.beachcongetion.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.lee.beachcongetion.R
import com.lee.beachcongetion.ui.adapter.BeachRecyclerAdapter
import com.lee.beachcongetion.databinding.FragmentAllBeachesBinding
import com.lee.beachcongetion.ui.factory.BeachViewModelFactory
import com.lee.beachcongetion.data.retrofit.model.beach.BeachCongestionModel
import com.lee.beachcongetion.ui.viewmodel.BeachViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.MapViewEventListener
import net.daum.mf.map.api.MapView.OpenAPIKeyAuthenticationResultListener
import net.daum.mf.map.api.MapView.POIItemEventListener

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
            val marker = MapPOIItem()
            with(marker){
                itemName = it[0].placeName
                tag = 0
                mapPoint = MapPoint.mapPointWithCONGCoord(poi.latitude.toDouble() , poi.longitude.toDouble())
                markerType = MapPOIItem.MarkerType.BluePin
                selectedMarkerType = MapPOIItem.MarkerType.RedPin
            }
            mMap.addPOIItem(marker)
            mMap.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithCONGCoord(poi.longitude.toDouble() , poi.latitude.toDouble())
                , 17
                ,true)
        }
    }

    /**
     * Function that initialize RecyclerView
     * **/
    private fun initRecyclerView() {
        val snapHelper = PagerSnapHelper()
        mBeachRecyclerAdapter = BeachRecyclerAdapter()
        with(binding) {
            beachRecyclerView.layoutManager = LinearLayoutManager(context , RecyclerView.VERTICAL, false)
            // Click listener for open google map
            mBeachRecyclerAdapter.setOnItemClickListener(object : BeachRecyclerAdapter.OnItemClickListener{
                override fun onItemClick(v: View, data: BeachCongestionModel, pos: Int) {
                    val selectedBeachName = data.poiNm + "해수욕장"
                    mBeachViewModel.getKakaoPoiList(resources.getString(R.string.kakao_api_key) , selectedBeachName)
                }
            })
            beachRecyclerView.adapter = mBeachRecyclerAdapter
            snapHelper.attachToRecyclerView(beachRecyclerView)
        }
    }
}