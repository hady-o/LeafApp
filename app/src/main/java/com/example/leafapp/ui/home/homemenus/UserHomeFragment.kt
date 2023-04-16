package com.example.leafapp.ui.home.homemenus

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.leafapp.Constants
import com.example.leafapp.R
import com.example.leafapp.SharedPref
import com.example.leafapp.adapters.PsAdapter
import com.example.leafapp.admin.DeletePostViewModel
import com.example.leafapp.databinding.FragmentUserHomeBinding

import com.example.leafapp.ui.home.AllFragmentViewModel
import com.example.leafapp.ui.home.HomeFragmentDirections
//import com.example.leafapp.ui.home.homemenus.position
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast


class UserHomeFragment : Fragment() {
    private val viewModel: AllFragmentViewModel by lazy {
        ViewModelProvider(this).get(AllFragmentViewModel::class.java)
    }
    private val deleteViewModel: DeletePostViewModel by lazy {
        ViewModelProvider(this).get(DeletePostViewModel::class.java)
    }
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentUserHomeBinding.inflate(layoutInflater)
        binding.all.background= resources.getDrawable(R.drawable.button_shape5g)
        binding.lifecycleOwner = this
        binding.vie=viewModel
        viewModel.getAllPost()
        CurrItem.deleteEnable = false
        val adapter= PsAdapter(PsAdapter.PostListenerClass {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetalsFragment(it))
        },viewModel,deleteViewModel)



        CurrItem.deletedPost.observe(viewLifecycleOwner){
            FancyToast.makeText(requireContext(),"hi from userFragment ${CurrItem.deletedPost.value?.title}",
                FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show()
            CurrItem.deletedPost.value?.let { it1 ->
                var newList = viewModel.removePost(it1)
                adapter.submitList(newList)
                binding.allRC.adapter?.notifyDataSetChanged()
            }
        }

        // search edittext
        binding.searchBtnUserHome.setOnClickListener()
        {
            getFragment(DiseaseFragment())
        }
        // set user profile button
        binding.userImage.setOnClickListener()
        {
            SharedPref.fromWhereToProfile= Constants.HOME
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_profileFragment)
        }
        // set user data
        var user = FirebaseAuth.getInstance().currentUser
        binding.userNameId.text = user!!.displayName
        if(user.photoUrl!=null)
        {
            Glide.with(requireContext())
                .load(user.photoUrl)
                .into(binding.userImage)
        }
        binding.all.setOnClickListener(){
            viewModel.getAllPost()
            binding.all.background= resources.getDrawable(R.drawable.button_shape5g)
            binding.care.background= resources.getDrawable(R.drawable.button_shape5)
            binding.treatment.background= resources.getDrawable(R.drawable.button_shape5)
            binding.landscape.background= resources.getDrawable(R.drawable.button_shape5)
        }
        binding.care.setOnClickListener(){
            viewModel.getPost("care")
            binding.all.background= resources.getDrawable(R.drawable.button_shape5)
            binding.care.background= resources.getDrawable(R.drawable.button_shape5g)
            binding.treatment.background= resources.getDrawable(R.drawable.button_shape5)
            binding.landscape.background= resources.getDrawable(R.drawable.button_shape5)
        }
        binding.treatment.setOnClickListener(){
            viewModel.getPost("treatment")
            binding.all.background= resources.getDrawable(R.drawable.button_shape5)
            binding.care.background= resources.getDrawable(R.drawable.button_shape5)
            binding.treatment.background= resources.getDrawable(R.drawable.button_shape5g)
            binding.landscape.background= resources.getDrawable(R.drawable.button_shape5)
        }
        binding.landscape.setOnClickListener(){
            viewModel.getPost("landscape")
            binding.all.background= resources.getDrawable(R.drawable.button_shape5)
            binding.care.background= resources.getDrawable(R.drawable.button_shape5)
            binding.treatment.background= resources.getDrawable(R.drawable.button_shape5)
            binding.landscape.background= resources.getDrawable(R.drawable.button_shape5g)
        }
        binding.allRC.adapter = adapter
        binding.allRC.layoutManager!!.scrollToPosition(CurrItem.pos)
        return binding.root
    }

    private fun getFragment(dest: Fragment) {
        var frag = parentFragmentManager
        var tran = frag.beginTransaction()
        tran.replace(R.id.frameId, dest)
        tran.commit()
    }
    override fun onResume() {
        super.onResume()

    }



}