package com.lee.beachcongetion.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.lee.beachcongetion.R
import com.lee.beachcongetion.adapter.BeachRecyclerAdapter
import com.lee.beachcongetion.databinding.FragmentAllBeachesBinding
import com.lee.beachcongetion.retrofit.BeachCongestionService
import com.lee.beachcongetion.retrofit.model.BeachCongestionModel
import com.lee.beachcongetion.viewmodel.BeachModel
import kotlinx.coroutines.*
import kotlin.random.Random

class AllBeachesFragment : Fragment() {
    private val TAG = "AllBeachesFragment"
    private lateinit var binding : FragmentAllBeachesBinding

    private lateinit var mBeachRecyclerAdapter: BeachRecyclerAdapter
    private lateinit var mBeachViewModel : BeachModel

    private var mJob : Job? = null
    private var mBeachList : MutableList<BeachCongestionModel>? = null

    companion object{
        fun newInstance() = AllBeachesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_all_beaches , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initBeachViewModel()
    }

    override fun onResume() {
        super.onResume()
        receiveBeachCongestion()
    }

    /**
     * Function for init viewModel
     * **/

    private fun initBeachViewModel(){
        mBeachViewModel = ViewModelProvider(this)[BeachModel::class.java]
        binding.beachListViewModel = mBeachViewModel
        mBeachViewModel.beachList.observe(viewLifecycleOwner , Observer {
            mBeachRecyclerAdapter.setList(it)
            mBeachRecyclerAdapter.notifyItemRangeChanged(0 , mBeachRecyclerAdapter.itemCount)
        })
    }

    /**
     * Function that initialize RecyclerView
     * **/
    private fun initRecyclerView() {
        val snapHelper = PagerSnapHelper()
        mBeachRecyclerAdapter = BeachRecyclerAdapter()
        with(binding) {
            beachRecyclerView.layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL, false)
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

    /**
     *  Function that get Beach congestion with Coroutine
     * **/
    private fun receiveBeachCongestion() {
        Log.d(TAG, "receiveBeachCongestion()")
        mJob = CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "receiveBeachCongestion: start coroutine")
            val response = BeachCongestionService.getInstance().getBeachCongestion()
            response.body()?.getAllBeachList()?.let {
                mBeachList = it
            }
            // For testing function that beach congestion light
            mBeachList?.forEach {
                it.congestion = Random.nextInt(1,4).toString()
            }
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    Log.d(TAG, "receiveBeachCongestion: Success!!")
                    response.body()?.let {
                       mBeachViewModel.setBeachViewModelList(mBeachList!!)
                    }?:let {
                        Log.d(TAG, "receiveBeachCongestion: response body is null!!")
                    }
                    mJob?.cancel()
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context , "정상적으로 정보를 불러왔습니다." , Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context , "서버에서 정보를 가져오지 못했습니다." , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}