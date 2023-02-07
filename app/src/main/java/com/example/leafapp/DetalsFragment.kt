package com.example.leafapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.leafapp.databinding.FragmentDetalsBinding
import com.example.leafapp.databinding.FragmentLoginBinding

class DetalsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentDetalsBinding.inflate(layoutInflater)
        // binding.constraintLayout.background = requireContext().getDrawable(R.drawable.img)

        return binding.root
    }

}