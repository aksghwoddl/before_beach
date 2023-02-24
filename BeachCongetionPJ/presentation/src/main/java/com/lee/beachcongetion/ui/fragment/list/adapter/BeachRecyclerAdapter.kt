package com.lee.beachcongetion.ui.fragment.list.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
import com.lee.beachcongetion.R
import com.lee.beachcongetion.common.Congestion
import com.lee.beachcongetion.databinding.BeachItemBinding
import com.lee.domain.model.beach.Beach

class BeachRecyclerAdapter : RecyclerView.Adapter<BeachRecyclerAdapter.BeachViewpagerViewHolder>() {

    private var beachList = arrayListOf<Beach>()
    private var onItemClickListener : OnItemClickListener? = null
    private var onButtonClickListener : OnItemClickListener? = null

    interface OnItemClickListener{
        fun onClick(v:View, data: Beach , pos : Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener) {
        onItemClickListener = listener
    }

    fun setOnButtonClickListener(listener: OnItemClickListener) {
        onButtonClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeachViewpagerViewHolder {
        val binding = BeachItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return BeachViewpagerViewHolder(binding , onItemClickListener!! , onButtonClickListener!!)
    }

    override fun onBindViewHolder(holder: BeachViewpagerViewHolder, position: Int) {
        holder.bind(beachList[position])
    }


    override fun getItemCount() = beachList.size

    fun setList(list : ArrayList<Beach>){
        beachList = list
    }

    class BeachViewpagerViewHolder(
        private val binding : BeachItemBinding ,
        private val onItemClickListener: OnItemClickListener ,
        private val onButtonClickListener : OnItemClickListener
        ) : RecyclerView.ViewHolder(binding.root){
        fun bind(model: Beach){
            with(binding){
                beachName.text = model.poiNm
                congestionTextView.run {
                    when(model.congestion){
                        Congestion.NORMAL.congest -> {
                            setTextColor(Color.parseColor(binding.root.context.getString(R.string.normal_color)))
                            text = binding.root.context.getString(R.string.normal)
                        }
                        Congestion.FEW_CONGESTION.congest -> {
                            setTextColor(Color.parseColor(binding.root.context.getString(R.string.few_congest_color)))
                            text = binding.root.context.getString(R.string.few_congest)
                        }
                        Congestion.CONGEST.congest -> {
                            setTextColor(Color.parseColor(binding.root.context.getString(R.string.congest_color)))
                            text = binding.root.context.getString(R.string.congest)
                        }
                        else -> {
                            throw java.lang.IllegalArgumentException("잘못된 상태입니다!")
                        }
                    }
                }
            }
            addListeners(model)
        }

        private fun addListeners(data : Beach) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                itemView.setOnClickListener {
                    onItemClickListener.onClick(itemView , data  , position)
                }
                binding.findRoadButton.setOnClickListener {
                    onButtonClickListener.onClick(it , data , position)
                }
            }
        }
    }
}