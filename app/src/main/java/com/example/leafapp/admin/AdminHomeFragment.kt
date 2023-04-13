package com.example.leafapp.admin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.leafapp.R
import com.example.leafapp.databinding.FragmentAddPostBinding
import com.example.leafapp.databinding.FragmentAdminHomeBinding

class AdminHomeFragment : Fragment() {

    companion object {
        fun newInstance() = AdminHomeFragment()
    }

    private lateinit var viewModel: AdminHomeViewModel
    private lateinit var binding: FragmentAdminHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AdminHomeViewModel::class.java)
        binding = FragmentAdminHomeBinding.inflate(layoutInflater)

        binding.addPostBtn.setOnClickListener{

            Navigation.findNavController(binding.root).navigate(R.id.action_adminHomeFragment_to_addPostFragment)
        }

        binding.deletePostBtn.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_adminHomeFragment_to_deletePostFragment)
        }


        return binding.root
    }



}