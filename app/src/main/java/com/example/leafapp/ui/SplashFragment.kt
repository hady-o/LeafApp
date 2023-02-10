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
import com.example.leafapp.ui.home.homemenus.position


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentSplashBinding.inflate(layoutInflater)
        position = 0
        Handler(Looper.getMainLooper()).postDelayed({
           Navigation.findNavController(binding.root).navigate(R.id.action_splashFragment_to_loginFragment)
        }, 3000)

        return binding.root
    }

}