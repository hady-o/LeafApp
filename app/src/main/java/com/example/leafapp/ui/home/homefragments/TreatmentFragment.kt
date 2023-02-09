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
import com.example.leafapp.databinding.FragmentTreatmentBinding
import com.example.leafapp.ui.home.HomeFragmentDirections
import com.example.leafapp.ui.home.homefragments.allFragment.AllFragmentViewModel


class TreatmentFragment : Fragment() {

    lateinit var binding: FragmentTreatmentBinding
    private val viewModel: AllFragmentViewModel by lazy {
        ViewModelProvider(this).get(AllFragmentViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTreatmentBinding.inflate(layoutInflater)
        viewModel.getPost("treatment")
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter= PsAdapter(PsAdapter.PostListenerClass {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetalsFragment(it))
        })
        binding.treatmentRC.adapter = adapter
        return binding.root
    }

}