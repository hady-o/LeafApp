package com.example.leafapp

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leafapp.adapters.PostAdapter
import com.example.leafapp.dataclass.PostClass

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<PostClass>) {
    val adapter = recyclerView.adapter as PostAdapter
//    adapter.submitList(data)
}