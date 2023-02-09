package com.example.leafapp.ui.home.homefragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.leafapp.R
import com.example.leafapp.adapters.PsAdapter
import com.example.leafapp.databinding.FragmentCareBinding
import com.example.leafapp.databinding.FragmentLandScapingBinding
import com.example.leafapp.ui.home.HomeFragmentDirections
import com.example.leafapp.ui.home.homefragments.allFragment.AllFragmentViewModel


class LandScapingFragment : Fragment() {

    lateinit var binding: FragmentLandScapingBinding
    private val viewModel: AllFragmentViewModel by lazy {
        ViewModelProvider(this).get(AllFragmentViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLandScapingBinding.inflate(layoutInflater)
        viewModel.getPost("landscape")
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter= PsAdapter(PsAdapter.PostListenerClass {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetalsFragment(it))
        })
        binding.landscapeRC.adapter = adapter
        return binding.root
    }


}