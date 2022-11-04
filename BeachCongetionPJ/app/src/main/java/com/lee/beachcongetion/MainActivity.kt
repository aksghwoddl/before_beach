package com.lee.beachcongetion

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lee.beachcongetion.adapter.BeachFragmentStateAdapter
import com.lee.beachcongetion.databinding.ActivityMainBinding
import com.lee.beachcongetion.fragments.BeachCongestionFragmentForViewPager
import com.lee.beachcongetion.retrofit.BeachCongestionService
import com.lee.beachcongetion.retrofit.model.BeachCongestionModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding : ActivityMainBinding
    private lateinit var mFragmentStateAdapter: BeachFragmentStateAdapter

    private var mJob : Job? = null
    private var mBeachList = mutableListOf<BeachCongestionModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        mFragmentStateAdapter = BeachFragmentStateAdapter(this)
        binding.progressBar.visibility = View.VISIBLE
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
                        mBeachList.forEach{
                            val fragment = BeachCongestionFragmentForViewPager.newInstance(it.poiNm , it.congestion)
                            mFragmentStateAdapter.appendFragment(fragment)
                            binding.beachViewPager.adapter = mFragmentStateAdapter
                        }
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
}