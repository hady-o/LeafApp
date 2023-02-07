package com.example.leafapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.leafapp.databinding.HistoryCardBinding
import com.example.leafapp.dataclass.PlantClass

class PlantAdapter(plants:List<PlantClass>) : RecyclerView.Adapter<PlantAdapter.MyViewHolder>() {
    var plants: List<PlantClass> = ArrayList<PlantClass>()
    init {
        this.plants =plants
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HistoryCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlantAdapter.MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder)
        {
           with(plants.get(position))
           {
               //userImage
                Glide.with(holder.itemView.context)
                    .load(this.photo)
                    .into(binding.plantImage)
               binding.plantDate.text=this.date
               binding.plantNameId.text= this.name
               binding.plantDisID.text = this.disease
            }
        }
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    class MyViewHolder(val binding: HistoryCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}