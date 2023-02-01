package com.example.leafapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.leafapp.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentSignUpBinding.inflate(layoutInflater)


        // buttons clicks
        //sign in button
        binding.signIntBtnIdInBtnId.setOnClickListener()
        {
            Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_loginFragment2)
        }
        //sign up button
        binding.signUpBtnId.setOnClickListener()
        {
            signUp(binding.nameEditText,binding.emailEditText,binding.passEditText,binding.root,binding.progressBar)
        }
        //show pass button
        binding.showBtn.setOnClickListener()
        {
            showPassword(binding.passEditText)
        }
        //cancel button
        binding.cancelBtnId.setOnClickListener()
        {
            Navigation.findNavController(binding.root).navigate(R.id.action_signUpFragment_to_loginFragment2)
        }


        return binding.root
    }
}