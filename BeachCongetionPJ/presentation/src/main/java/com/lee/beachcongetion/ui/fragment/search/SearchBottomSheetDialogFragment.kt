package com.lee.beachcongetion.ui.fragment.search

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lee.beachcongetion.BuildConfig
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.Utils
import com.lee.beachcongetion.common.base.BaseBottomSheetDialogFragment
import com.lee.beachcongetion.databinding.FragmentSearchBinding
import com.lee.beachcongetion.ui.fragment.list.adapter.BeachRecyclerAdapter
import com.lee.beachcongetion.ui.fragment.search.viewmodel.SearchViewModel
import com.lee.data.common.Navi
import com.lee.domain.model.beach.Beach
import com.lee.domain.model.beach.BeachList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class SearchBottomSheetDialogFragment(private val beachList : BeachList) : BaseBottomSheetDialogFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel : SearchViewModel by viewModels()
    private lateinit var beachRecyclerAdapter: BeachRecyclerAdapter

    companion object{
        fun newInstance(beachList : BeachList) = SearchBottomSheetDialogFragment(beachList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchDialog  = this@SearchBottomSheetDialogFragment
        initRecyclerView()
        viewModel.setBeachList(beachList)
        viewModel.getSelectedNavi()
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

            poiList.observe(viewLifecycleOwner) { // 해변 근처 POI 정보들
                with(Intent()){
                    action = Utils.ACTION_MARK_BEACH_PIN
                    putExtra(Utils.EXTRA_SELECTED_POI , it)
                    requireActivity().sendBroadcast(this)
                    this@SearchBottomSheetDialogFragment.dismiss()
                }
            }

            destination.observe(viewLifecycleOwner){ // 목적지
                var url = ""
                currentNavi.value?.let { navi ->
                    when(navi){
                        Navi.KAKAO_MAP.name -> {
                            url = "kakaomap://search?q=${it.placeName}&p=${it.latitude},${it.longitude}"
                            Utils.startNavigationWithIntent(url , requireActivity() , navi)
                        }
                        Navi.TMAP.name -> {
                            url = "tmap://search?name=${it.placeName}"
                            Utils.startNavigationWithIntent(url , requireActivity() , navi)
                        }
                    }
                }
            }

            searchList.observe(viewLifecycleOwner){ // 검색된 해변 목록
                if(it.isNotEmpty()){
                    with(binding){
                        searchListRecyclerView.visibility = View.VISIBLE
                        noSearchItemImageView.visibility = View.GONE
                    }
                    beachRecyclerAdapter.run {
                        setList(it)
                        notifyItemRangeChanged(0 , itemCount)
                    }
                } else {
                    with(binding){
                        searchListRecyclerView.visibility = View.INVISIBLE
                        noSearchItemImageView.visibility = View.VISIBLE
                    }
                }
            }

            toastMessage.observe(viewLifecycleOwner){ // Toast Message
                Toast.makeText(requireContext() , it , Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 리스너 등록하는 함수
     * **/
    override fun addListeners() {
        with(binding){
            searchTextLayout.setEndIconOnClickListener { // Clear Button
                searchTextView.text.run {
                    if(this.isNotEmpty()){
                        clear()
                    }
                }
            }

            searchTextView.setOnKeyListener { _, keyCode , _ -> // EditText 검색 클릭시
                when(keyCode){
                    KeyEvent.KEYCODE_ENTER -> {
                        searchTextView.text.isEmpty().let {
                            if(it){ // 검색어가 비어있을때
                                viewModel.setToastMessage(getString(R.string.input_keyword))
                            } else { // 검색어가 비어있지 않을때
                                val searchList = arrayListOf<Beach>()
                                val beachListFlow = beachList.mBeachList.asFlow()
                                lifecycleScope.launch{
                                    val result = withContext(Dispatchers.Default){
                                        beachListFlow.filter { beach -> // 모든 해변목록
                                            beach.poiNm.contains(searchTextView.text)
                                        }
                                    }
                                    result.collect{ filteredBeach -> // 필터링된 해변 목록
                                        searchList.add(filteredBeach)
                                    }
                                    viewModel.setSearchList(searchList)
                                    searchTextView.dismissDropDown()
                                }
                            }
                        }
                        Utils.hideSoftInputKeyboard(requireContext() , view?.windowToken) // 키보드 숨기기
                    }
                }
                false
            }

            searchTextView.setOnItemClickListener { _, view , _ , _ ->
                val selectedView = view as TextView
                val beachFlow = beachList.mBeachList.asFlow()
                val searchList = arrayListOf<Beach>()
                lifecycleScope.launch {
                    val result = withContext(Dispatchers.Default){
                        beachFlow.filter { // 모든 해변 목록
                            it.poiNm == selectedView.text
                        }
                    }
                    result.collect{ // 필터링된 해변 목록
                        searchList.add(it)
                    }
                    viewModel.setSearchList(searchList)
                }
            }
        }
    }

    private fun initRecyclerView() {
        beachRecyclerAdapter = BeachRecyclerAdapter()
        beachRecyclerAdapter.run {
            setOnItemClickListener(object : BeachRecyclerAdapter.OnItemClickListener{
                override fun onClick(v: View, data: Beach, pos: Int) {
                    val selectedBeachName = data.poiNm + getString(R.string.beach)
                    viewModel.getKakaoPoiList(BuildConfig.KAKAO_API_KEY , selectedBeachName , false)
                }
            })
            setOnButtonClickListener( object : BeachRecyclerAdapter.OnItemClickListener {
                override fun onClick(v: View, data: Beach, pos: Int) {
                    val selectedBeachName = data.poiNm + getString(R.string.beach)
                    viewModel.getKakaoPoiList(BuildConfig.KAKAO_API_KEY , selectedBeachName , true)
                }
            })
        }
        binding.searchListRecyclerView.run {
            adapter = beachRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null // update시에 나타나는 깜빡임 현상 제거
        }
    }
}