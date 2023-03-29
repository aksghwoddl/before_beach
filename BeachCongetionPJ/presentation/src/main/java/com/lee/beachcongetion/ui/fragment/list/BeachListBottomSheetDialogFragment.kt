package com.lee.beachcongetion.ui.fragment.list
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lee.beachcongetion.BuildConfig
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.Utils
import com.lee.beachcongetion.common.base.BaseBottomSheetDialogFragment
import com.lee.beachcongetion.databinding.FragmentBeachListBinding
import com.lee.beachcongetion.ui.fragment.list.adapter.BeachRecyclerAdapter
import com.lee.beachcongetion.ui.fragment.list.viewmodel.BeachListViewModel
import com.lee.data.common.Navi
import com.lee.domain.model.beach.Beach
import com.lee.domain.model.beach.BeachList
import dagger.hilt.android.AndroidEntryPoint

/**
 * 해수욕장 목록을 보여주는 BottomSheetDialogFragment
 * **/
@AndroidEntryPoint
class BeachListBottomSheetDialogFragment(private val beachList: BeachList) : BaseBottomSheetDialogFragment<FragmentBeachListBinding>(R.layout.fragment_beach_list) {
    private lateinit var beachRecyclerAdapter : BeachRecyclerAdapter
    private val viewModel : BeachListViewModel by viewModels()

    companion object{
        fun newInstance(beachList: BeachList) = BeachListBottomSheetDialogFragment(beachList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listBottomSheetDialog = this@BeachListBottomSheetDialogFragment
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
            beachList.observe(viewLifecycleOwner) { // 해변 목록
                beachRecyclerAdapter.setList(it.mBeachList)
                beachRecyclerAdapter.notifyItemRangeChanged(0 , beachRecyclerAdapter.itemCount)
            }

            poiList.observe(viewLifecycleOwner) { // 해변 근처 POI 정보들
                with(Intent()){
                    action = Utils.ACTION_MARK_BEACH_PIN
                    putExtra(Utils.EXTRA_SELECTED_POI , it)
                    requireActivity().sendBroadcast(this)
                    this@BeachListBottomSheetDialogFragment.dismiss()
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

            toastMessage.observe(viewLifecycleOwner){ // Toast Message
                android.widget.Toast.makeText(requireContext() , it , android.widget.Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * 리스너 등록하는 함수
     * **/
    override fun addListeners() {

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

        binding.beachRecyclerView.run {
            layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL, false)
            adapter = beachRecyclerAdapter
        }
    }
}