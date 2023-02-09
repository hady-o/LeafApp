package com.example.leafapp.ui.home.homefragments.allFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.leafapp.adapters.PsAdapter
import com.example.leafapp.databinding.FragmentAllBinding
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.ui.home.HomeFragment
import com.example.leafapp.ui.home.HomeFragmentDirections
import com.example.leafapp.ui.home.homemenus.UserHomeFragmentDirections


class AllFragment : Fragment() {


    lateinit var bunding: FragmentAllBinding



    private val viewModel: AllFragmentViewModel by lazy {
        ViewModelProvider(this).get(AllFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bunding = FragmentAllBinding.inflate(layoutInflater)
        bunding.lifecycleOwner = this
        bunding.viewModel = viewModel
        viewModel.getAllPost()
        val adapter= PsAdapter(PsAdapter.PostListenerClass {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetalsFragment(it))
        })
        bunding.allRC.adapter = adapter
        return bunding.root
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
    }
}