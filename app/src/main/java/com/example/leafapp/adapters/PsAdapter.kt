package com.example.leafapp.adapters

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leafapp.databinding.PostCardBinding
import com.example.leafapp.dataclass.PostClass


class PsAdapter(val clickListener: PostListenerClass) :
    ListAdapter<PostClass, PsAdapter.ViewHolder>(PostDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromViewHolder(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var post = getItem(position)!!
        holder.postBind(post, clickListener)
        holder.itemView.setOnClickListener{
            clickListener.onClick(post)
            com.example.leafapp.ui.home.homemenus.CurrItem.pos = position
        }
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

        fun postBind(
            post: PostClass?, clickListener: PostListenerClass
        ) {
            binding.post = post
            binding.executePendingBindings()
//            Firebase.firestore.collection("postInfo")
//                .document().collection("info")
//                .add(info)
            binding.shareImBtn.setOnClickListener {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                share.putExtra(Intent.EXTRA_TEXT, post!!.title+"\n\n"+post.contents)
                binding.root.context.startActivity(Intent.createChooser(share, "Share using"))

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