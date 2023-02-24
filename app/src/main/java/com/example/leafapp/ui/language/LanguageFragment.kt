package com.example.leafapp.ui.language

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.leafapp.Constants
import com.example.leafapp.R
import com.example.leafapp.SharedPref
import com.example.leafapp.databinding.FragmentLanguageBinding

class LanguageFragment : Fragment(),View.OnClickListener {
    lateinit var binding: FragmentLanguageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_language, container, false)
        binding.lifecycleOwner = this
        setClickListeners()
        return binding.root
    }

    private fun setClickListeners() {
        binding.btnEnglish.setOnClickListener(this)
        binding.btnArabic.setOnClickListener(this)
    }

    private fun navigateToSignUpFragment(language: String) {
        if (language.equals(SharedPref.language, true).not()) requireActivity().recreate()
        SharedPref.isLanguageSelected = true
        SharedPref.language = language
        this.findNavController()
            .navigate(LanguageFragmentDirections.actionLanguageFragmentToViewPagerFragment())
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.btnEnglish.id -> {
                navigateToSignUpFragment(Constants.ENGLISH)
            }
            binding.btnArabic.id -> {
                navigateToSignUpFragment(Constants.ARABIC)
            }
        }
    }
}