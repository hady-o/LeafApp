package com.example.leafapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.graphics.createBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.leafapp.databinding.FragmentHomeBinding
import com.example.leafapp.homefragments.*
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {
    public lateinit var binding: FragmentHomeBinding
    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentHomeBinding.inflate(layoutInflater)
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        var tabLayoutAdapter = TabLayoutAdapter(parentFragmentManager)
        tabLayoutAdapter.addFragment(AllFragment(), "All")
        tabLayoutAdapter.addFragment(CareFragment(), "Care")
        tabLayoutAdapter.addFragment(TreatmentFragment(), "Treatment")
        tabLayoutAdapter.addFragment(LandScapingFragment(), "Landscaping")
        binding.viewPager.adapter = tabLayoutAdapter
        binding.myTab.setupWithViewPager(binding.viewPager)

        // set user profile button
        binding.userImage.setOnClickListener()
        {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_profileFragment)
        }
        // set user data
        var user =FirebaseAuth.getInstance().currentUser
        binding.welcomeTextId.setText("Welcome back"+" "+user!!.displayName)
        if(user.photoUrl!=null)
        {
            Glide.with(requireContext())
                .load(user.photoUrl)
                .into(binding.userImage)
        }

        // cam btn
        binding.camBtn.setOnClickListener()
        {
            if (checkSelfPermission(requireContext(),Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }
        }


        return binding.root
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(requireContext(), "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }
   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
           val photo = data!!.extras!!["data"] as Bitmap?
       }
   }
}

