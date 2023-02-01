package com.example.leafapp

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.leafapp.databinding.FragmentLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : Fragment() {
    private val LOGINCODE = 1001
    private lateinit var client: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentLoginBinding.inflate(layoutInflater)

        val  options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.Sign_In_With_Google))
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(requireActivity(),options)




        // buttons clicks

        //sign up button
        binding.signUpBtnId!!.setOnClickListener()
        {
            Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_signUpFragment2)
        }
        //sign in button
        binding.signIntBtnId!!.setOnClickListener()
        {
            signIn(binding.emailEditText,binding.passEditText,binding.root, binding.progressBar)
        }
        //show pass button
        binding.showBtn.setOnClickListener()
        {
            showPassword(binding.passEditText)
        }
        //google sign in button
        binding.googleSignInBtnId!!.setOnClickListener()
        {

        }



        return binding.root
    }


    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }


}