package com.example.leafapp.ui

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment

import com.example.leafapp.R

import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

import com.example.leafapp.databinding.FragmentHomeBinding
import com.example.leafapp.homemenus.HistoryFragment
import com.example.leafapp.homemenus.UserHomeFragment

import com.example.leafapp.savePhoto

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.coroutines.CoroutineStart
import org.checkerframework.common.returnsreceiver.qual.This
import java.io.ByteArrayOutputStream
import java.security.spec.PSSParameterSpec.DEFAULT

import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100
    var image: Bitmap? = null
    public lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        getFragment(UserHomeFragment())
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        // cam btn
        binding.camBtn.setOnClickListener()
        {
            if (checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            } else {
                val cameraIntent = CropImage.activity().getIntent(this.requireContext())

                startActivityForResult(cameraIntent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
//                this.activity?.let { it1 -> CropImage.activity().start(it1) }
//                startActivityForResult(CropImage.)

            }
        }






        binding.navBarId.setOnItemSelectedListener() {

            if (it.itemId == R.id.homeMenuId) {
                getFragment(UserHomeFragment())
            } else if (it.itemId == R.id.plantMenuId) {
                getFragment(HistoryFragment())
            }
            true
        }


        return binding.root
    }

    fun getFragment(dest: Fragment) {
        var frag = parentFragmentManager
        var tran = frag.beginTransaction()
        tran.replace(R.id.frameId, dest)
        tran.commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "camera permission granted", Toast.LENGTH_LONG)
                    .show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(requireContext(), "camera permission denied", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == AppCompatActivity.RESULT_OK) {
                if (result != null) {
                    val uri = result.uri //path of image in phone

                    val bitmap = MediaStore.Images.Media.getBitmap(
                        this.requireActivity().contentResolver,
                        uri
                    )
//                    val sdf = SimpleDateFormat("dd/M/yyyy")
//                    val currentDate = sdf.format(Date())
//                    //  binding.userImage.setImageBitmap()
//                    savePhoto(bitmap!!, currentDate)
                    this.findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToResultAndTips2(bitmap))


                }
            }
        }

        /*if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo = data!!.extras!!["data"] as Bitmap?
            image = photo

            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToResultAndTips2(image!!))



 //           val sdf = SimpleDateFormat("dd/M/yyyy")
 //           val currentDate = sdf.format(Date())
 //         //  binding.userImage.setImageBitmap()
 //           Toast.makeText(requireContext(), data!!.extras!!["data"].toString(), Toast.LENGTH_LONG).show()
 //
 //           savePhoto(image!!,currentDate)

        }
        else{
             Toast.makeText(this.context,"Fail",Toast.LENGTH_LONG).show()
        }*/
    }


}

