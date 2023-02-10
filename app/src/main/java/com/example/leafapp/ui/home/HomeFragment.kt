package com.example.leafapp.ui.home

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.lifecycle.ViewModelProvider

import com.example.leafapp.R

import androidx.navigation.fragment.findNavController

import com.example.leafapp.databinding.FragmentHomeBinding
import com.example.leafapp.posts.PostDao
import com.example.leafapp.ui.home.homemenus.HistoryFragment
import com.example.leafapp.ui.home.homemenus.UserHomeFragment

import com.theartofdev.edmodo.cropper.CropImage


class HomeFragment : Fragment() {
    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100
    var image: Bitmap? = null
    lateinit var binding: FragmentHomeBinding
    private val viewModel: AllFragmentViewModel by lazy {
        ViewModelProvider(this).get(AllFragmentViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        getFragment(UserHomeFragment())
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        PostDao.PostRoomDataBase.getInstance(requireContext()).dao.inserPosts(PostClass("first one",5,4,",","care","","",false))
//        PostDao.PostRoomDataBase.getInstance(requireContext()).dao.inserPosts(PostClass("first one",5,4,",","treatment","","",false))

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
                val cameraIntent = CropImage.activity().getIntent(this.requireContext())
                startActivityForResult(cameraIntent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
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
                    this.findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToResultAndTips2(bitmap,true))
                }
            }
        }
    }



    override fun onResume() {
        super.onResume()

        Toast.makeText(requireContext(),"home resume",Toast.LENGTH_LONG).show()
    }

}

