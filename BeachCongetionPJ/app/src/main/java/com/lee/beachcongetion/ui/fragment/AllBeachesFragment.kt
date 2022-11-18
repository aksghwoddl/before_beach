package com.lee.beachcongetion.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.lee.beachcongetion.ui.adapter.BeachRecyclerAdapter
import com.lee.beachcongetion.databinding.FragmentAllBeachesBinding
import com.lee.beachcongetion.ui.factory.BeachViewModelFactory
import com.lee.beachcongetion.data.retrofit.model.beach.BeachCongestionModel
import com.lee.beachcongetion.ui.viewmodel.BeachViewModel
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

        // For toast message when ocurr error
        mBeachViewModel.errorMessage.observe(viewLifecycleOwner , Observer {
            Toast.makeText(context , "서버에서 정상적으로 정보를 가져오지 못했습니다. : $it", Toast.LENGTH_SHORT).show()
        })
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
                    val gmmUri = Uri.parse(String.format("geo:37.7749,-122.4194?q=%s", Uri.encode(data.poiNm + "해수욕장")))
                    with(Intent(Intent.ACTION_VIEW , gmmUri)){
                        setPackage("com.google.android.apps.maps")
                        startActivity(this)
                    }
                }
            })
            beachRecyclerView.adapter = mBeachRecyclerAdapter
            snapHelper.attachToRecyclerView(beachRecyclerView)
        }
    }
}