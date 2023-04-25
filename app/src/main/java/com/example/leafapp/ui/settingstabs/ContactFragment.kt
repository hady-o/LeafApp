package com.example.leafapp.ui.settingstabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.leafapp.R
import com.example.leafapp.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentContactBinding.inflate(layoutInflater)

        binding.backBtn.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(ContactFragmentDirections.actionContactFragmentToAboutUsFragment())
        }

        return binding.root
    }
}