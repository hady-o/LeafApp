package com.example.leafapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leafapp.R
import com.example.leafapp.databinding.PostCardBinding
import com.example.leafapp.dataclass.PostClass

class PsAdapter(val clickListener: PostListenerClass) :
    ListAdapter<PostClass, PsAdapter.ViewHolder>(PostDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromViewHolder(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var post = getItem(position)!!
        holder.astroidBind(post, clickListener)
    }


    class ViewHolder private constructor(val binding: PostCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun fromViewHolder(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun astroidBind(
            post: PostClass?, clickListener: PostListenerClass
        ) {
            binding.post = post
            binding.click = clickListener
            binding.executePendingBindings()
            binding.likeImBtn.setOnClickListener {
                if (!post!!.isLike) {
                    post.likePressed()
                    binding.likeCouter.text = post!!.getLikes()
                    binding.likeImBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                    post.isLike = true
                } else {
                    post.likeRelased()
                    binding.likeCouter.text = post.getLikes()
                    binding.likeImBtn.setImageResource(R.drawable.ic_sharp_favorite_border_24)
                    post.isLike = false
                }
            }
        }
    }

    class PostDiffCallBack : DiffUtil.ItemCallback<PostClass>() {
        override fun areItemsTheSame(oldItem: PostClass, newItem: PostClass): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: PostClass, newItem: PostClass): Boolean {
            return oldItem == newItem
        }

    }

    class PostListenerClass(val clickListener: (post: PostClass) -> Unit) {
        fun onClick(post: PostClass) = clickListener(post)
    }


}