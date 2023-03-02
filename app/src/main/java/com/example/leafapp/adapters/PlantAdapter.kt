package com.example.leafapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.leafapp.R
import com.example.leafapp.databinding.HistoryCardBinding
import com.example.leafapp.dataclass.PlantClass

class PlantAdapter(val clickListener: HistoryListenerClass) :
    ListAdapter<PlantClass, PlantAdapter.ViewHolder>(PostDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromViewHolder(parent)

    }

    //
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var plant = getItem(position)!!
        holder.plantBind(plant, clickListener)
        holder.itemView.setOnClickListener{
            clickListener.onClick(plant)
        }
    }

    class ViewHolder private constructor(val binding: HistoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun fromViewHolder(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HistoryCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun plantBind(
            plant: PlantClass?, clickListener: HistoryListenerClass
        ) {
            binding.plant = plant
        }
    }

    class PostDiffCallBack : DiffUtil.ItemCallback<PlantClass>() {
        override fun areItemsTheSame(oldItem: PlantClass, newItem: PlantClass): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PlantClass, newItem: PlantClass): Boolean {
            return oldItem == newItem
        }

    }

    class HistoryListenerClass(val clickListener: (post: PlantClass) -> Unit) {
        fun onClick(post: PlantClass) = clickListener(post)
    }


}