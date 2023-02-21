package com.example.leafapp.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.leafapp.databinding.FragmentDetalsBinding
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.ImagesPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin
import java.io.BufferedReader
import java.io.InputStreamReader
import android.content.res.AssetManager
import androidx.lifecycle.ViewModelProvider
import com.example.leafapp.ui.home.AllFragmentViewModel
import com.shashank.sony.fancytoastlib.FancyToast


class DetalsFragment : Fragment() {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetalsBinding.inflate(inflater)
        var contents: MutableList<String> = ArrayList()
        var topics: MutableList<String> = ArrayList()
        val markwon: Markwon =
            Markwon.builder(requireContext()) // automatically create Glide instance
                .usePlugin(ImagesPlugin.create())
                .usePlugin(GlideImagesPlugin.create(requireContext())) // use supplied Glide instance
                .usePlugin(GlideImagesPlugin.create(Glide.with(requireContext())))
                .usePlugin(HtmlPlugin.create())
                .build()
        // binding.constraintLayout.background = requireContext().getDrawable(R.drawable.img)
        val args = arguments?.let {
            DetalsFragmentArgs.fromBundle(
                it
            )
        }

        args?.let {
            binding.post = it.post
            // val postContent = readFromAsset(requireContext().assets,it.post.doc)
            FancyToast.makeText(requireContext(),it.post.doc,FancyToast.LENGTH_LONG,FancyToast.CONFUSING,true).show()
            markwon.setMarkdown(binding.markdownTxt,it.post.contents)
            /*if(!postContent.equals(" # Hi From The Trap state :/ ")){
                val viewModel: AllFragmentViewModel by lazy {
                    ViewModelProvider(this).get(AllFragmentViewModel::class.java)
                }
                val postt = it.post
                postt.contents = postContent
                viewModel.addPost(postt)
            }*/

        }

       
        return binding.root
    }

   /* private fun readFromAsset(asset: AssetManager,id:String): String {
        try {
            val inputStream = InputStreamReader(asset.open("Posts md/${id}.md"))
            val reader = BufferedReader(inputStream)
            val data = reader.use {
                it.readText()
            }
            return data
        }
        catch (e: Exception){
            return " # Hi From The Trap state :/ "
        }


    }*/

}