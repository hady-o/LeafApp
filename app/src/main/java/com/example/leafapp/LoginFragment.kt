package com.example.leafapp

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.leafapp.databinding.FragmentLoginBinding
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
    private val LOGINCODE = 505
    private lateinit var client: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentLoginBinding.inflate(layoutInflater)

        val  options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
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
            Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_homeFragment)
        }
        //google sign in button
        binding.googleSignInBtnId!!.setOnClickListener()
        {
            val intent = client.signInIntent
            startActivityForResult(intent,LOGINCODE)

        }



        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOGINCODE) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_loginFragment_to_homeFragment)

                        } else {
                            Toast.makeText(
                                requireContext(),
                                task.exception?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }

        }




    }
    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }


}