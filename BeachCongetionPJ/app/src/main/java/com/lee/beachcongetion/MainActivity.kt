package com.lee.beachcongetion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.lee.beachcongetion.adapter.BeachRecyclerAdapter
import com.lee.beachcongetion.databinding.ActivityMainBinding
import com.lee.beachcongetion.retrofit.BeachCongestionService
import com.lee.beachcongetion.retrofit.model.BeachCongestionModel
import kotlinx.coroutines.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding : ActivityMainBinding
    private lateinit var mBeachRecyclerAdapter: BeachRecyclerAdapter

    private var mJob : Job? = null
    private var mBeachList = mutableListOf<BeachCongestionModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        mBeachRecyclerAdapter = BeachRecyclerAdapter()
        binding.progressBar.visibility = View.VISIBLE
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        receiveBeachCongestion()
    }

    /**
     *  Function that get Beach congestion with Coroutine
     * **/
    private fun receiveBeachCongestion() {
        mJob = CoroutineScope(Dispatchers.IO).launch {
            val response = BeachCongestionService.getInstance().getBeachCongestion()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.let {
                        mBeachList = response.body()!!.getAllBeachList()
                        // For testing function that beach congestion light
                        mBeachList.forEach {
                            it.congestion = Random.nextInt(1,4).toString()
                        }
                        mBeachRecyclerAdapter.setList(mBeachList)
                        mBeachRecyclerAdapter.notifyItemRangeChanged(0 , mBeachRecyclerAdapter.itemCount)
                    }?:let {
                        Log.d(TAG, "receiveBeachCongestion: response body is null!!")
                    }
                    mJob?.cancel()
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@MainActivity , "정상적으로 정보를 불러왔습니다." , Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity , "서버에서 정보를 가져오지 못했습니다." , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Function that initialize RecyclerView
     * **/
    private fun initRecyclerView() {
        val snapHelper = PagerSnapHelper()
        with(binding) {
            beachRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
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