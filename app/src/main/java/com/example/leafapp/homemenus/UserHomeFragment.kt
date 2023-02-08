package com.example.leafapp.homemenus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.leafapp.R
import com.example.leafapp.databinding.FragmentUserHomeBinding
import com.example.leafapp.homefragments.*
import com.example.leafapp.homefragments.allFragment.AllFragment
import com.google.firebase.auth.FirebaseAuth


class UserHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentUserHomeBinding.inflate(layoutInflater)
        var tabLayoutAdapter = TabLayoutAdapter(parentFragmentManager)
        tabLayoutAdapter.addFragment(AllFragment(), "All")
        tabLayoutAdapter.addFragment(CareFragment(), "Care")
        tabLayoutAdapter.addFragment(TreatmentFragment(), "Treatment")
        tabLayoutAdapter.addFragment(LandScapingFragment(), "Landscaping")
        binding.viewPager.adapter = tabLayoutAdapter
        binding.myTab.setupWithViewPager(binding.viewPager)


        binding.editText.setOnClickListener()
        {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_detalsFragment2)
        }
        // set user profile button
        binding.userImage.setOnClickListener()
        {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_profileFragment)
        }
        // set user data
        var user = FirebaseAuth.getInstance().currentUser
        binding.welcomeTextId.text = "Welcome back"+" "+user!!.displayName
        if(user.photoUrl!=null)
        {
            Glide.with(requireContext())
                .load(user.photoUrl)
                .into(binding.userImage)
        }

        return binding.root
    }

}