package com.lee.beachcongetion.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lee.beachcongetion.common.*
import com.lee.beachcongetion.databinding.FragmentBeachItemBinding

class BeachCongestionFragmentForViewPager : Fragment() {
    private val TAG = "BeachCongestionFragmentForViewPager"

    private lateinit var binding : FragmentBeachItemBinding
    companion object{
        fun newInstance(beachNAme : String , congestion : String) : BeachCongestionFragmentForViewPager{
            val fragment = BeachCongestionFragmentForViewPager()
            val bundle  = Bundle()
            with(bundle){
                putString(EXTRA_BEACH_NAME , beachNAme)
                putString(EXTRA_CONGESTION , congestion)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView()")

        binding = FragmentBeachItemBinding.inflate(inflater , container , false)
        arguments?.let {
            Log.d(TAG, "onCreateView: Arguments is not null create fragment!!")
            binding.beachName.text = arguments?.getString(EXTRA_BEACH_NAME)
            binding.beachCongestion.text = when(arguments?.getString(EXTRA_CONGESTION)){
                NORMAL -> {
                    "보통"
                }
                FEW_CONGEST -> {
                    "약간 혼잡"
                }
                CONGEST -> {
                    "매우 혼잡"
                }
                else -> {
                    "Error"
                }
            }
        }
        return binding.root
    }
}