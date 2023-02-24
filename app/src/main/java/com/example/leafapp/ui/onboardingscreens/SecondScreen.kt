package com.example.leafapp.ui.onboardingscreens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.leafapp.R
import com.example.leafapp.SharedPref
import com.example.leafapp.databinding.FragmentSecondScreenBinding


class SecondScreen : Fragment() {

    private lateinit var secondScreenBinding:FragmentSecondScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        secondScreenBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_second_screen, container, false)
        val viewPager =  activity?.findViewById<ViewPager2>(R.id.viewPager)

        secondScreenBinding.next2.setOnClickListener {
            viewPager?.currentItem = 2
        }
        secondScreenBinding.skip2.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
            onBoardingFinished()
        }
        return secondScreenBinding.root
    }

    private fun onBoardingFinished(){
        SharedPref.isBoardingFinished=true
    }
}