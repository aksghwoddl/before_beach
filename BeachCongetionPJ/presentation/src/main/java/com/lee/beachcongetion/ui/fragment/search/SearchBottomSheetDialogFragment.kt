package com.lee.beachcongetion.ui.fragment.search

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.Utils
import com.lee.beachcongetion.common.base.BaseBottomSheetDialogFragment
import com.lee.beachcongetion.databinding.FragmentSearchBinding
import com.lee.beachcongetion.ui.fragment.search.viewmodel.SearchViewModel
import com.lee.domain.model.beach.BeachList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBottomSheetDialogFragment(private val beachList : BeachList) : BaseBottomSheetDialogFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel : SearchViewModel by viewModels()
    companion object{
        fun newInstance(beachList : BeachList) = SearchBottomSheetDialogFragment(beachList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchDialog  = this@SearchBottomSheetDialogFragment
        viewModel.setBeachList(beachList)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            Utils.setupRatio(bottomSheetDialog , requireActivity())
        }
        return dialog
    }

    /**
     * LiveData 관찰하는 함수
     * **/
    override fun observeData() {
        with(viewModel){
            beachList.observe(viewLifecycleOwner){
                val list = arrayListOf<String>()
                it.mBeachList.forEach { beach ->
                    list.add(beach.poiNm)
                }
                val adapter = ArrayAdapter(requireContext() , android.R.layout.simple_dropdown_item_1line, list)
                binding.searchTextView.setAdapter(adapter)
            }
        }
    }

    /**
     * 리스너 등록하는 함수
     * **/
    override fun addListeners() {
        with(binding){
            searchTextLayout.setEndIconOnClickListener {
                searchTextView.text.run {
                    if(this.isNotEmpty()){
                        clear()
                    }
                }
            }
        }
    }
}