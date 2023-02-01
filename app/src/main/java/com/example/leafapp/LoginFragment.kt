package com.example.leafapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.leafapp.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


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
        binding.showBtn!!.setOnClickListener()
        {
            showPassword(binding.passEditText)
        }
        //google sign in button
        binding.googleSignInBtnId!!.setOnClickListener()
        {

        }
        //forgot password button
        binding.forgotPassBtnId!!.setOnClickListener()
        {
           resetPassword(binding.emailEditText,requireActivity())
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