package com.example.leafapp

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.leafapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentProfileBinding.inflate(layoutInflater)
        //set slogan colored text
        val surName = getColoredSpanned("deference", "#6BDBAB")
        binding.slogan.text = Html.fromHtml("To gather we can<br>make"+" "+surName)
        //user data
        var user = FirebaseAuth.getInstance().currentUser
        binding.nameEditText.setText(user!!.displayName.toString())
        binding.emailEditText.setText(user!!.email.toString())
        Glide.with(requireContext())
            .load(user.photoUrl)
            .into(binding.userImage)

        binding.cancelBtnId.setOnClickListener()
        {
            FirebaseAuth.getInstance().signOut()
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_loginFragment)
        }
        return binding.root
    }
    private fun getColoredSpanned(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }
}