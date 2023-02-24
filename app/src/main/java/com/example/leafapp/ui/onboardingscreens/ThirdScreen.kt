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
import com.example.leafapp.databinding.FragmentThirdScreenBinding

class ThirdScreen : Fragment() {

    private lateinit var thirdScreenBinding: FragmentThirdScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        thirdScreenBinding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_third_screen, container, false)

        thirdScreenBinding.next3.setOnClickListener {
            onBoardingFinished()
        }
        thirdScreenBinding.skip3.setOnClickListener {
            onBoardingFinished()
        }
        return thirdScreenBinding.root
    }

    private fun onBoardingFinished(){
        findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
        SharedPref.isBoardingFinished=true
    }
}