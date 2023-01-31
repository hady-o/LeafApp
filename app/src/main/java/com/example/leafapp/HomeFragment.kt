package com.example.leafapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.Fragment
import com.example.leafapp.databinding.FragmentHomeBinding
import com.example.leafapp.homefragments.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentHomeBinding.inflate(layoutInflater)


        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        var tabLayoutAdapter = TabLayoutAdapter(parentFragmentManager)
        tabLayoutAdapter.addFragment(AllFragment(), "All")
        tabLayoutAdapter.addFragment(CareFragment(), "Care")
        tabLayoutAdapter.addFragment(TreatmentFragment(), "Treatment")
        tabLayoutAdapter.addFragment(LandScapingFragment(), "Landscaping")




        binding.viewPager.adapter = tabLayoutAdapter
        binding.myTab.setupWithViewPager(binding.viewPager)


        return binding.root
    }
}