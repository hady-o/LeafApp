package com.example.leafapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.leafapp.R
import com.example.leafapp.ResultAndTips.ResultAndTipsArgs
import com.example.leafapp.databinding.FragmentDetalsBinding
import com.example.leafapp.databinding.FragmentLoginBinding

class DetalsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentDetalsBinding.inflate(inflater)

        // binding.constraintLayout.background = requireContext().getDrawable(R.drawable.img)
        val args = arguments?.let {
            DetalsFragmentArgs.fromBundle(
                it
            )
        }
        args?.let {
            binding.post = it.post
            Glide.with(binding.root)
                .load(it.post.photo)
                .into(binding.imageView2)
        }
        binding.backBtn.setOnClickListener(){
            Navigation.findNavController(binding.root).navigate(R.id.action_detalsFragment_to_homeFragment)
        }
        return binding.root
    }

}