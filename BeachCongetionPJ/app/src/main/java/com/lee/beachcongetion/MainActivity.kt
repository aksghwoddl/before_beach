package com.lee.beachcongetion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lee.beachcongetion.adapter.BeachFragmentStateAdapter
import com.lee.beachcongetion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mFragmentStateAdapter: BeachFragmentStateAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        mFragmentStateAdapter = BeachFragmentStateAdapter(this)
    }

    override fun onResume() {
        super.onResume()
    }

    fun receiveBeachCongestion() {

    }
}