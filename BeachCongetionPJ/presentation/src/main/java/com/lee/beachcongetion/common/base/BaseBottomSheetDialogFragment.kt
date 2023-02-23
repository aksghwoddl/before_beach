package com.lee.beachcongetion.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Base BottomSheetDialogFragment class
 * **/
abstract class BaseBottomSheetDialogFragment<T : ViewDataBinding>(@LayoutRes private val layoutRes : Int) : BottomSheetDialogFragment() {
    private var _binding : T? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater , layoutRes , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        addListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /**
     * LiveData 관찰하는 함수
     * **/
    abstract fun observeData()

    /**
     * 리스너 등록하는 함수
     * **/
    abstract fun addListeners()

}