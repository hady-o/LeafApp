package com.example.leafapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.leafapp.R
import com.example.leafapp.databinding.FragmentSplashBinding



class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentSplashBinding.inflate(layoutInflater)
//        position = 0
        Handler(Looper.getMainLooper()).postDelayed({
           navigateFromSplash()
        }, 3000)

        return binding.root
    }

    private fun navigateFromSplash(){
        Navigation.findNavController(binding.root).navigate(R.id.action_splashFragment_to_viewPagerFragment)
    }
}