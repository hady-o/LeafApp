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
import androidx.navigation.Navigation

import com.example.leafapp.R

import androidx.navigation.fragment.findNavController

import com.example.leafapp.databinding.FragmentHomeBinding
import com.example.leafapp.posts.PostDao
import com.example.leafapp.ui.ResultFragment.ResultAndTipsArgs
import com.example.leafapp.ui.SettingsFragment
import com.example.leafapp.ui.home.homemenus.DiseaseFragment
import com.example.leafapp.ui.home.homemenus.HistoryFragment
import com.example.leafapp.ui.home.homemenus.UserHomeFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
        val args = arguments?.let {HomeFragmentArgs.fromBundle(it) }
        args?.let {
            when(it.dest){
                0 -> getFragment(HistoryFragment())
                1 -> getFragment(DiseaseFragment())
                2 -> getFragment(SettingsFragment())
                else -> getFragment(UserHomeFragment())
            }
        }

        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // PostDao.PostRoomDataBase.getInstance(requireContext()).dao.inserPosts(PostClass("first one",5,4,",","care","","",false))
        // PostDao.PostRoomDataBase.getInstance(requireContext()).dao.inserPosts(PostClass("first one",5,4,",","treatment","","",false))
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
//        viewModel.allPosts.value?.forEach{
//
//        }
        binding.navBarId.setOnItemSelectedListener() {
            when(it.itemId){
                R.id.homeMenuId -> getFragment(UserHomeFragment())
                R.id.plantMenuId -> getFragment(HistoryFragment())
                R.id.menuId -> getFragment(SettingsFragment())
                R.id.dessIdMenu -> getFragment(DiseaseFragment())
            }
            true
        }
        return binding.root
    }

    private fun getFragment(dest: Fragment) {
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
                Toast.makeText(requireContext(), getString(R.string.camera_granted), Toast.LENGTH_LONG)
                    .show()
                val cameraIntent = CropImage.activity().getIntent(this.requireContext())
                startActivityForResult(cameraIntent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
            } else {
                Toast.makeText(requireContext(), getString(R.string.camera_denied), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(requireContext(),"done1",Toast.LENGTH_LONG).show()
            val result = CropImage.getActivityResult(data)
            if (resultCode == AppCompatActivity.RESULT_OK) {
                Toast.makeText(requireContext(),"done2",Toast.LENGTH_LONG).show()
                if (result != null) {

                    val uri = result.uri //path of image in phone
                    this.findNavController()
                        .navigate(
                            HomeFragmentDirections.actionHomeFragmentToResultAndTips2(
                                uri.toString(),
                                true,
                                null
                            )
                        )
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
    }

}

