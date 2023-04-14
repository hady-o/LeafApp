package com.example.leafapp.adapters

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leafapp.R
import com.example.leafapp.databinding.PostCardBinding
import com.example.leafapp.dataclass.PostClass

import com.example.leafapp.ui.home.homemenus.CurrItem
import com.shashank.sony.fancytoastlib.FancyToast

import com.example.leafapp.ui.home.AllFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class PsAdapter(val clickListener: PostListenerClass,val viewModel: AllFragmentViewModel) :
    ListAdapter<PostClass, PsAdapter.ViewHolder>(PostDiffCallBack()) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromViewHolder(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var post = getItem(position)!!
        holder.postBind(post, clickListener,viewModel)
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
            post: PostClass?, clickListener: PostListenerClass, viewModel: AllFragmentViewModel
        ) {
            binding.post = post

            binding.shareImBtn.setOnClickListener {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                share.putExtra(Intent.EXTRA_TEXT, post!!.title+"\n\n"+post.contents)
                binding.root.context.startActivity(Intent.createChooser(share, "Share using"))

            }


            if(!CurrItem.deleteEnable){
                binding.deletePostBtn.visibility = View.GONE
                binding.executePendingBindings()

                Firebase.firestore.collection("fav")
                    .document("${FirebaseAuth.getInstance().currentUser!!.uid}_${post!!.doc}")
                    .get().addOnCompleteListener(){
                        try {
                            val x =it.result.getString("title")!!
                            binding.likeImBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                        }catch (e:Exception){
                            binding.likeImBtn.setImageResource(R.drawable.ic_sharp_favorite_border_24)
                        }
                    }
                binding.likeImBtn.setOnClickListener(){
                    var x = ""
                    Firebase.firestore.collection("fav")
                        .document("${FirebaseAuth.getInstance().currentUser!!.uid}_${post!!.doc}")
                        .get().addOnCompleteListener(){
                            try {
                                x =it.result.getString("title")!!
                                viewModel.deleteFromFav(post)
                                binding.likeImBtn.setImageResource(R.drawable.ic_sharp_favorite_border_24)
                            }catch (e:Exception){
                                viewModel.addToFav(post)
                                binding.likeImBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                            }

                        }

                }

            }else{
                binding.deletePostBtn.visibility = View.VISIBLE
                binding.deletePostBtn.setOnClickListener{
                    /* FancyToast.makeText(binding.root.context,"you try to delete : ${post?.title}"
                         ,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show()*/
                    CurrItem.deletedPost.value = post;
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