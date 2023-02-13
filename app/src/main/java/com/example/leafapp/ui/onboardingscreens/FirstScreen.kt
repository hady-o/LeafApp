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
import com.example.leafapp.databinding.FragmentFirstScreenBinding


class FirstScreen : Fragment() {

    private lateinit var firstScreenBinding: FragmentFirstScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firstScreenBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_first_screen, container, false)
        val viewPager =  activity?.findViewById<ViewPager2>(R.id.viewPager)

        firstScreenBinding.next.setOnClickListener {
            viewPager?.currentItem = 1
        }
        firstScreenBinding.skip.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
            onBoardingFinished()
        }
        return firstScreenBinding.root
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}