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
import com.example.leafapp.DiseasesData.lookUpList
import com.example.leafapp.R
import com.example.leafapp.SharedPref
import com.example.leafapp.databinding.FragmentSettingsBinding
import com.example.leafapp.ui.home.HomeFragmentDirections
import com.example.leafapp.ui.home.homemenus.HistoryFragment
import com.example.leafapp.ui.home.homemenus.UserHomeFragment
import com.example.leafapp.utils.setLocale
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var tempLanguage: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        setCheckedButton(SharedPref.language)
        setClickListeners()
        return binding.root
    }

    private fun setClickListeners() {
        binding.rbEnglish.setOnClickListener(this)
        binding.rbArabic.setOnClickListener(this)
        binding.signOut.setOnClickListener(this)
        binding.btnProfile.setOnClickListener(this)
        binding.btnAboutUs.setOnClickListener(this)
        binding.btnHelp.setOnClickListener(this)
        //binding.btnChat.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.rbEnglish.id -> {
                tempLanguage = Constants.ENGLISH
                //if SharedPref language is different recreate activity
                if (SharedPref.language.equals(Constants.ARABIC, true)) {
                    changeLanguage()
                }
                SharedPref.language = tempLanguage
            }
            binding.rbArabic.id -> {
                tempLanguage = Constants.ARABIC
                //if SharedPref language is different recreate activity
                if (SharedPref.language.equals(Constants.ENGLISH, true)) {
                    changeLanguage()
                }
                SharedPref.language = tempLanguage
            }
            binding.signOut.id -> {
                FirebaseAuth.getInstance().signOut()
                AuthUI.getInstance().signOut(requireContext())
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_homeFragment_to_loginFragment)
            }
            binding.btnProfile.id -> {
                SharedPref.fromWhereToProfile = Constants.SETTINGS
                Navigation.findNavController(binding.root)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
            }
            binding.btnAboutUs.id -> {
                Navigation.findNavController(binding.root)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToAboutUsFragment())
            }
            binding.btnHelp.id -> {
                Navigation.findNavController(binding.root)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToHelpFragment())
            }
            /*binding.btnChat.id -> {
                Navigation.findNavController(binding.root)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToContactFragment())
            }
            binding.postAddingBtn.id -> {
                /*Navigation.findNavController(binding.root)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToAboutUsFragment())*/
                /*Navigation.findNavController(binding.root)
                   .navigate(HomeFragmentDirections.actionHomeFragmentToAddPostFragment())*/
            }*/
        }
    }

    private fun changeLanguage() {
        requireActivity().setLocale(tempLanguage)
        requireActivity().recreate()
        lookUpList.clear()
    }

    private fun setCheckedButton(language: String) {
        if (language.equals(Constants.ENGLISH, true)) {
            binding.rbEnglish.isChecked = true
        } else {
            binding.rbArabic.isChecked = true
        }
    }

}