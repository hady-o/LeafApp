package com.example.leafapp.admin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.leafapp.R
import com.example.leafapp.adapters.PsAdapter
import com.example.leafapp.databinding.FragmentDeletePostBinding
import com.example.leafapp.databinding.FragmentDetalsBinding
import com.example.leafapp.databinding.FragmentUserHomeBinding
import com.example.leafapp.ui.home.AllFragmentViewModel
import com.example.leafapp.ui.home.HomeFragmentDirections
import com.example.leafapp.ui.home.homemenus.CurrItem
import com.shashank.sony.fancytoastlib.FancyToast

class DeletePostFragment : Fragment() {

    companion object {
        fun newInstance() = DeletePostFragment()
    }

    private val viewModel: AllFragmentViewModel by lazy {
        ViewModelProvider(this).get(AllFragmentViewModel::class.java)
    }
    private lateinit var binding:FragmentDeletePostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDeletePostBinding.inflate(layoutInflater)
        CurrItem.deleteEnable = true
        val adapter= PsAdapter(PsAdapter.PostListenerClass {
            this.findNavController().navigate(DeletePostFragmentDirections.actionDeletePostFragmentToDetalsFragment(it))
        },viewModel)
        viewModel.getAllPost()
        adapter.submitList(viewModel.allPosts.value)



        CurrItem.deletedPost.observe(viewLifecycleOwner){
            FancyToast.makeText(requireContext(),"hi from userFragment ${CurrItem.deletedPost.value?.title}",
                FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show()
            CurrItem.deletedPost.value?.let { it1 ->
                var newList = viewModel.removePost(it1)
                adapter.submitList(newList)
                binding.deletePostRc.adapter?.notifyDataSetChanged()

            }
        }

        binding.deleteAll.setOnClickListener{
            viewModel.getAllPost()
            adapter.submitList(viewModel.allPosts.value)
            binding.deletePostRc.adapter?.notifyDataSetChanged()
        }

        binding.deletePostRc.adapter = adapter

        return binding.root
    }



}