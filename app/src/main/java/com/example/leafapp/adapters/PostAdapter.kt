package com.example.leafapp.adapters

/*import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leafapp.databinding.PostCardBinding
import com.example.leafapp.dataclass.PostClass


class PostAdapter(val onClickListener: PostAdapter.OnClickListener) :
    ListAdapter<PostClass, PostAdapter.PostAdapterViewHolder>(DiffCallback) {


    companion object DiffCallback : DiffUtil.ItemCallback<PostClass>() {
        override fun areItemsTheSame(oldItem: PostClass, newItem: PostClass): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PostClass, newItem: PostClass): Boolean {
            return oldItem == newItem
        }

    }

    class PostAdapterViewHolder(val binding: PostCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PostClass) {
            binding.post = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapterViewHolder {
        return PostAdapterViewHolder(PostCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PostAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClickListener.onClick(getItem(position))
        }
    }

    class OnClickListener(val clickListener: (item: PostClass) -> Unit) {
        fun onClick(item: PostClass) = clickListener(item)
    }

}*/


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.leafapp.databinding.PostCardBinding
import com.example.leafapp.dataclass.PostClass
class PostAdapter(plants:List<PostClass>) : RecyclerView.Adapter<PostAdapter.MyViewHolder>() {
    var posts: List<PostClass> = ArrayList<PostClass>()
    init {
        this.posts =plants
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostAdapter.MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder)
        {
            with(posts.get(position))
            {
                //userImage
                binding.post = this

            }
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class MyViewHolder(val binding: PostCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}
