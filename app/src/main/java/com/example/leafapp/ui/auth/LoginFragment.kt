package com.example.leafapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.example.leafapp.R
import com.example.leafapp.authentication.Resource
import com.example.leafapp.databinding.FragmentLoginBinding
import com.example.leafapp.resetPassword
import com.example.leafapp.showPassword
import com.example.leafapp.signIn
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.observeOn

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val LOGINCODE = 1001
    private lateinit var client: GoogleSignInClient
    private val viewModel by viewModels<AuthViewModel>()
    lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(layoutInflater)

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.Sign_In_With_Google))
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(requireActivity(), options)



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
            resetPassword(binding.emailEditText, requireActivity())
        }




        return binding.root

    }


    override fun onStart() {
        super.onStart()
        viewModel.loginState!!.observe(viewLifecycleOwner) {
            when(it)
            {
                is Resource.Fail -> {Toast.makeText(requireContext(),"invalid data",Toast.LENGTH_LONG).show()
                    binding.progressBar.setVisibility(View.GONE)}
                is Resource.Load ->{binding.progressBar.setVisibility(View.VISIBLE)}
                is Resource.Success -> {
                    Toast.makeText(requireContext()," data",Toast.LENGTH_LONG).show()
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                }

            }
        }
    }

    }
