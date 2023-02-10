package com.example.leafapp.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.leafapp.R
import com.example.leafapp.ResultAndTips.ResultAndTipsArgs
import com.example.leafapp.databinding.FragmentDetalsBinding
import com.example.leafapp.databinding.FragmentLoginBinding

class DetalsFragment : Fragment() {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentDetalsBinding.inflate(inflater)
        var contents: MutableList<String>  = ArrayList()
        var topics : MutableList<String> = ArrayList()
        // binding.constraintLayout.background = requireContext().getDrawable(R.drawable.img)
        val args = arguments?.let {
            DetalsFragmentArgs.fromBundle(
                it
            )
        }
        args?.let {
            binding.post = it.post
            contents = it.post.contents.split("_/_") as MutableList<String>
            topics = it.post.topics.split(','   ) as MutableList<String>
            if(contents.size!= topics.size) {
                if(contents.size>topics.size){
                    for(i in 0 until contents.size-topics.size){
                        topics.add(" ")
                    }
                }else{
                    for(i in 0 until topics.size-contents.size){
                        topics.add(" ")
                    }
                }
            }
        }

        for (i in 0 until topics.size){
            var tmpTopic = topics[i]
            var tmpContent = contents[i]

            val tpicTV = TextView(requireContext())
            tpicTV.text = tmpTopic
            tpicTV.setTextAppearance(R.style.sub_hider_txt)

            val contentTv = TextView(requireContext())
            contentTv.text = tmpContent
            contentTv.setTextAppearance(R.style.content_txt)

            binding.contentLv.addView(tpicTV)
            binding.contentLv.addView(contentTv)

        }
        binding.backBtn.setOnClickListener(){
            Navigation.findNavController(binding.root).navigate(R.id.action_detalsFragment_to_homeFragment)
        }
        return binding.root
    }

}