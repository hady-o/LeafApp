package com.example.leafapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.unit.Constraints
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.leafapp.Constants
import com.example.leafapp.R
import com.example.leafapp.SharedPref
import com.example.leafapp.databinding.FragmentSettingsBinding
import com.example.leafapp.ui.home.HomeFragmentDirections
import com.example.leafapp.ui.home.homemenus.HistoryFragment
import com.example.leafapp.ui.home.homemenus.UserHomeFragment
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment(),View.OnClickListener{

    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_settings, container, false)
        setCheckedButton(SharedPref.language)
        setClickListeners()
        return binding.root
    }

    private fun setClickListeners() {
        binding.rbEnglish.setOnClickListener(this)
        binding.rbArabic.setOnClickListener(this)
        binding.btnLogOut.setOnClickListener(this)
        binding.btnProfile.setOnClickListener(this)
        binding.btnAboutUs.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.rbEnglish.id -> {
                if(SharedPref.language.equals(Constants.ARABIC,true)) requireActivity().recreate()
                SharedPref.language=Constants.ENGLISH
            }
            binding.rbArabic.id -> {
                if(SharedPref.language.equals(Constants.ENGLISH,true)) requireActivity().recreate()
                SharedPref.language=Constants.ARABIC
            }
            binding.btnLogOut.id -> {
                FirebaseAuth.getInstance().signOut()
                AuthUI.getInstance().signOut(requireContext())
                Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_loginFragment)
            }
            binding.btnProfile.id -> {
                SharedPref.fromWhereToProfile=Constants.SETTINGS
                Navigation.findNavController(binding.root).navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
            }
            binding.btnAboutUs.id -> {
                Navigation.findNavController(binding.root).navigate(SettingsFragmentDirections.actionSettingsFragmentToAboutUsFragment())
            }
        }
    }

    private fun setCheckedButton(language:String){
        if(language.equals(Constants.ENGLISH,true)){
            binding.rbEnglish.isChecked=true
        }else{
            binding.rbArabic.isChecked=true
        }
    }
}