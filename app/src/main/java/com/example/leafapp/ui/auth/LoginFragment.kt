package com.example.leafapp.ui.auth

import android.app.Activity
import android.app.Activity.PERFORMANCE_HINT_SERVICE
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.example.leafapp.R
import com.example.leafapp.authentication.Resource
import com.example.leafapp.databinding.FragmentLoginBinding
import com.example.leafapp.resetPassword
import com.example.leafapp.showPassword
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    val SIGN_IN_RESULT_CODE =1001
    private lateinit var client: GoogleSignInClient
    private val viewModel by viewModels<AuthViewModel>()
    lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(layoutInflater)

        val googleProviders = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
            )

        val phoneProviders = arrayListOf(
            AuthUI.IdpConfig.PhoneBuilder().build(),

        )

// Create and launch sign-in intent


        // buttons clicks

        //sign up button
        binding.signUpBtnId!!.setOnClickListener()
        {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_loginFragment_to_signUpFragment2)
        }
        //sign in button
        binding.signIntBtnId!!.setOnClickListener()
        {
            viewModel.login(
                binding.emailEditText.text.toString(),
                binding.passEditText.text.toString()
            )

        }
        //show pass button
//        binding.showBtn!!.setOnClickListener()
//        {
//            showPassword(binding.passEditText)
//        }
        //google sign in button
        binding.googleLogin!!.setOnClickListener()
        {
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder().setTheme(R.style.Theme_LeafApp).setAvailableProviders(googleProviders)
                        .build(),
            SIGN_IN_RESULT_CODE
            )
        }

        binding.phoneLogin!!.setOnClickListener()
        {
            startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setTheme(R.style.Theme_LeafApp).setAvailableProviders(phoneProviders)
                    .build(),
                SIGN_IN_RESULT_CODE
            )
        }

        //forgot password button
        binding.forgotPassBtnId!!.setOnClickListener()
        {
            resetPassword(binding.emailEditText, requireActivity())
        }
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        viewModel.loginState!!.observe(viewLifecycleOwner) {
            when(it)
            {
                is Resource.Fail -> {if(it.ex is FirebaseNetworkException)
                {
                    FancyToast.makeText(requireContext(),"cheek yor internet connection",
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,true).show()
                }
                else
                    FancyToast.makeText(requireContext(),"invalid username or password",
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,true).show()

                    binding.progressBar.setVisibility(View.GONE)}
                is Resource.Load ->{binding.progressBar.setVisibility(View.VISIBLE)}
                is Resource.Success -> {
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                FancyToast.makeText(requireContext(),"you can complete yor data via profile screen",
                    FancyToast.LENGTH_LONG,
                    FancyToast.INFO,true).show()
            } else {
            }
        }
    }
}
