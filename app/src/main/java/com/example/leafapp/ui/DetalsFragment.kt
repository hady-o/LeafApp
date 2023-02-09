package com.example.leafapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//        val args = arguments?.let {
//            DetalsFragmentArgs.fromBundle(
//                it
//            )
//        }
        return binding.root
    }

}