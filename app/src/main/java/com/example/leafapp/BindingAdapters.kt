package com.example.leafapp

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.leafapp.adapters.PsAdapter
import com.example.leafapp.dataclass.PostClass


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<PostClass>) {
    val adapter = recyclerView.adapter as PsAdapter
     adapter.submitList(data)
}

@BindingAdapter("loadImage")
fun loadImage(imgView: ImageView,data:String){
    Glide.with(imgView.context)
        .load(data)
        .into(imgView)
}

@BindingAdapter("setBitmapImage")
fun setBitmapImage(imgView: ImageView, data:Bitmap?){
    if(data == null){
        imgView.setImageResource(R.drawable.good_plant)
    }
    else {
        imgView.setImageBitmap(data)
    }
}