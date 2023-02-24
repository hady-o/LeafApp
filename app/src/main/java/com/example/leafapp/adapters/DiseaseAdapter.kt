package com.example.leafapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leafapp.R
import com.example.leafapp.databinding.DiseaseItemBinding
import com.example.leafapp.databinding.PostCardBinding
import com.example.leafapp.dataclass.DiseaseClass
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.posts.PostDao
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DiseaseAdapter(val clickListener: DiseaseListenerClass) :
    ListAdapter<DiseaseClass, DiseaseAdapter.ViewHolder>(DiseaseDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromViewHolder(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var disease = getItem(position)!!
        holder.DiseaseBind(disease, clickListener)
        holder.itemView.setOnClickListener{
            clickListener.onClick(disease)
            //com.example.leafapp.ui.home.homemenus.CurrItem.pos = position
        }
    }


    class ViewHolder private constructor(val binding: DiseaseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun fromViewHolder(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DiseaseItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun DiseaseBind(
            disease: DiseaseClass?, clickListener: DiseaseListenerClass
        ) {
            binding.disease = disease
            binding.executePendingBindings()
        }
    }

    class DiseaseDiffCallBack : DiffUtil.ItemCallback<DiseaseClass>() {
        override fun areItemsTheSame(oldItem: DiseaseClass, newItem: DiseaseClass): Boolean {
            return oldItem.diseaseName == newItem.diseaseName
        }

        override fun areContentsTheSame(oldItem: DiseaseClass, newItem: DiseaseClass): Boolean {
            return oldItem == newItem
        }
    }

    class DiseaseListenerClass(val clickListener: (disease:DiseaseClass) -> Unit) {

        fun onClick(disease:DiseaseClass) = clickListener(disease)
    }


}