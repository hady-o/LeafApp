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
import com.example.leafapp.bindRecyclerView
import com.example.leafapp.databinding.FragmentAllBinding
import com.example.leafapp.databinding.FragmentCareBinding
import com.example.leafapp.ui.home.homefragments.allFragment.AllFragmentDirections
import com.example.leafapp.ui.home.homefragments.allFragment.AllFragmentViewModel


class CareFragment : Fragment() {
    lateinit var binding: FragmentCareBinding
    private val viewModel: AllFragmentViewModel by lazy {
        ViewModelProvider(this).get(AllFragmentViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCareBinding.inflate(layoutInflater)
        viewModel.getPost("care")
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter= PsAdapter(PsAdapter.PostListenerClass {
            this.findNavController().navigate(AllFragmentDirections.actionAllFragmentToDetalsFragment())
        })
        binding.allRC.adapter = adapter
        return binding.root
    }


}