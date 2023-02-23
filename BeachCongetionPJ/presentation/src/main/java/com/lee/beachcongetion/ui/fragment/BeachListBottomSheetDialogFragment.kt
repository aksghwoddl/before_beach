package com.lee.beachcongetion.ui.fragment
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.lee.beachcongetion.ui.activity.main.adapter.BeachRecyclerAdapter
import com.lee.beachcongetion.ui.fragment.viewmodel.BeachListViewModel
import com.lee.domain.model.beach.Beach
import dagger.hilt.android.AndroidEntryPoint


const val TAG = "BeachListFragment"

/**
 * 해수욕장 목록을 보여주는 BottomSheetDialogFragment
 * **/
@AndroidEntryPoint
class BeachListBottomSheetDialogFragment : BaseBottomSheetDialogFragment<FragmentBeachListBinding>(R.layout.fragment_beach_list) {
    private lateinit var beachRecyclerAdapter : BeachRecyclerAdapter
    private val viewModel : BeachListViewModel by viewModels()

    companion object{
        fun newInstance() = BeachListBottomSheetDialogFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listBottomSheetDialog = this@BeachListBottomSheetDialogFragment
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
        viewModel.getAllBeachCongestion()
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

            poiList.observe(viewLifecycleOwner) {
                with(Intent()){
                    action = Utils.ACTION_MARK_BEACH_PIN
                    putExtra(Utils.EXTRA_SELECTED_POI , it)
                    requireActivity().sendBroadcast(this)
                    this@BeachListBottomSheetDialogFragment.dismiss()
                }
            }

            isProgress.observe(viewLifecycleOwner){ // 진행 상태
                if(it){
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
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
        beachRecyclerAdapter.setOnItemClickListener(object : BeachRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: Beach, pos: Int) {
                val selectedBeachName = data.poiNm + getString(R.string.beach)
                viewModel.getKakaoPoiList(BuildConfig.KAKAO_API_KEY , selectedBeachName)
            }
        })
        binding.beachRecyclerView.run {
            layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL, false)
            adapter = beachRecyclerAdapter
        }
    }
}