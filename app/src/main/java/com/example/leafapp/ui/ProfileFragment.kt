package com.example.leafapp.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.leafapp.Constants
import com.example.leafapp.R
import com.example.leafapp.SharedPref
import com.example.leafapp.databinding.FragmentProfileBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException


class ProfileFragment : Fragment() {
    var image_uri: Uri? = null
    var imageServerUri: Uri? = null
    val chose_image = 101
    var editMode = false
    public lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentProfileBinding.inflate(layoutInflater)
        //set slogan colored text

        //user data
        var user = FirebaseAuth.getInstance().currentUser
        binding.user = user
        if(user!!.photoUrl!=null)
        {
            Glide.with(requireContext())
                .load(user.photoUrl)
                .into(binding.userImage)
            Glide.with(requireContext())
                .load(user.photoUrl)
                .into(binding.linearLayout)
        }
        binding.backBtn.setOnClickListener(){
            if(SharedPref.fromWhereToProfile.equals(Constants.HOME,true))
                this.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment(3))
            else if(SharedPref.fromWhereToProfile.equals(Constants.SETTINGS,true))
                this.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment(2))
        }
        //edit profile button

        binding.editBtn.setOnClickListener()
        {
            if(!editMode) {
                binding.editBtn.setImageResource(R.drawable.ic_baseline_check_24)
                editMode = true
                binding.passEditText.setVisibility(View.VISIBLE)
                //change user image
                binding.userImage.setOnClickListener()
                {
                    showImages()
                }
                binding.nameEditText.isEnabled = true
                binding.emailEditText.isEnabled = true
                binding.passEditText.isEnabled = true
            }
            else
            {
                updateProfile()
                binding.passEditText.setVisibility(View.GONE)
                binding.editBtn.setImageResource(R.drawable.ic_baseline_mode_edit_24)
                editMode = false
                //change user image
                binding.userImage.isClickable=false
                binding.nameEditText.isEnabled = false
                binding.emailEditText.isEnabled = false
                binding.passEditText.isEnabled = false
            }
        }



        return binding.root
    }


    // text color
    private fun getColoredSpanned(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }


    private fun showImages() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "select product photo"),chose_image
        )
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == chose_image && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            image_uri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), image_uri);
                binding.userImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    fun uploadphoto() {
        binding.progressBar.setVisibility(View.VISIBLE)
        binding.editBtn.setEnabled(false)
        val mStorageRef: StorageReference = FirebaseStorage.getInstance()
            .getReference("profiles/" + System.currentTimeMillis() + ".jpg")
        if (image_uri != null) {
            mStorageRef.putFile(image_uri!!).addOnCompleteListener()
            {
                it.addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener()
                    {
                       imageServerUri=it
                        var profile = UserProfileChangeRequest.Builder()
                            .setPhotoUri(imageServerUri)
                            .setDisplayName(binding.nameEditText.getText().toString())
                            .build()
                        FirebaseAuth.getInstance().currentUser!!.updateProfile(profile).addOnSuccessListener {
                            binding.progressBar.setVisibility(View.GONE)
                            binding.editBtn.setEnabled(true)
                        }
                    }
                }
            }
        }
    }
    fun updateProfile()
    {
        binding.progressBar.setVisibility(View.VISIBLE)
        var user = FirebaseAuth.getInstance().currentUser
        if(!binding.passEditText.text.isEmpty())
        {
            user!!.updatePassword(binding.passEditText.text.toString())
                .addOnCompleteListener()
                {

                }.addOnFailureListener()
                {
                    Toast.makeText(requireContext(),getString(R.string.invalid_pass),Toast.LENGTH_LONG).show()
                }
        }
        if(image_uri==null && user!!.displayName!!.equals(binding.nameEditText.text))
        {
            binding.progressBar.setVisibility(View.GONE)
            binding.editBtn.setEnabled(true)
        }
        else if(image_uri==null)
        {
            var profile = UserProfileChangeRequest.Builder()
                .setDisplayName(binding.nameEditText.getText().toString())
                .build()
            user!!.updateProfile(profile).addOnSuccessListener {
                binding.progressBar.setVisibility(View.GONE)
                binding.editBtn.setEnabled(true)
            }
        }
        else
        {
            uploadphoto()
            image_uri=null
        }
    }
}