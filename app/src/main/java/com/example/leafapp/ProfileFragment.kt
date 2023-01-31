package com.example.leafapp

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.leafapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentProfileBinding.inflate(layoutInflater)
        //set slogan colored text
       // val name = getColoredSpanned("To gather we can make", "#224637")
        val surName = getColoredSpanned("deference", "#6BDBAB")
        binding.slogan.text = Html.fromHtml("To gather we can<br>make"+" "+surName)
        return binding.root
    }
    private fun getColoredSpanned(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }
}