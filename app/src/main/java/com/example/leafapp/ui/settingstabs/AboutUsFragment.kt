package com.example.leafapp.ui.settingstabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.leafapp.R
import com.example.leafapp.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {

    lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutUsBinding.inflate(layoutInflater)

        binding.button2.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(AboutUsFragmentDirections.actionAboutUsFragmentToContactFragment())
        }

        return binding.root
    }


}