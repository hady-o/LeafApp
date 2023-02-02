package com.example.leafapp

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
import com.bumptech.glide.Glide
import com.example.leafapp.databinding.FragmentProfileBinding
import com.example.leafapp.dataclass.PlantClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
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
        val surName = getColoredSpanned("deference", "#6BDBAB")
        binding.slogan.text = Html.fromHtml("To gather we can<br>make"+" "+surName)
        //user data
        var user = FirebaseAuth.getInstance().currentUser

        binding.nameEditText.setText(user!!.displayName.toString())
        binding.emailEditText.setText(user!!.email.toString())
        if(user.photoUrl!=null)
        {
            Glide.with(requireContext())
                .load(user.photoUrl)
                .into(binding.userImage)
        }
        //log out
        binding.cancelBtnId.setOnClickListener()
        {
            FirebaseAuth.getInstance().signOut()
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_loginFragment)
        }
        //edit profile button

        binding.EditBtn.setOnClickListener()
        {
            if(!editMode) {
                binding.EditBtn.text = "Save"
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
                binding.EditBtn.text = "Edit"
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


    fun showImages() {
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
        binding.EditBtn.setEnabled(false)
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
                            binding.EditBtn.setEnabled(true)
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
                    Toast.makeText(requireContext(),"Invalid Password",Toast.LENGTH_LONG).show()
                }
        }
        if(image_uri==null && user!!.displayName!!.equals(binding.nameEditText.text))
        {
            binding.progressBar.setVisibility(View.GONE)
            binding.EditBtn.setEnabled(true)
        }
        else if(image_uri==null)
        {
            var profile = UserProfileChangeRequest.Builder()
                .setDisplayName(binding.nameEditText.getText().toString())
                .build()
            user!!.updateProfile(profile).addOnSuccessListener {
                binding.progressBar.setVisibility(View.GONE)
                binding.EditBtn.setEnabled(true)
            }
        }
        else
        {
            uploadphoto()
            image_uri=null
        }
    }
}