package com.example.leafapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.leafapp.admin.AddPostViewModel
import com.example.leafapp.databinding.FragmentAddPostBinding
import com.example.leafapp.dataclass.PostClass
import com.google.firebase.firestore.DocumentReference
import com.shashank.sony.fancytoastlib.FancyToast
import com.theartofdev.edmodo.cropper.CropImage
import io.noties.markwon.Markwon
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.MarkwonEditorTextWatcher
import kotlin.concurrent.timerTask

class AddPostFragment : Fragment() {

    private val MY_CAMERA_PERMISSION_CODE = 100
    private lateinit var viewModel: AddPostViewModel
    private lateinit var binding: FragmentAddPostBinding
    private var img_bitmap: Bitmap? = null
    private var uri: Uri? = null

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPostBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(AddPostViewModel::class.java)

        binding.addImage.setOnClickListener {
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

//        binding.previewBtn.setOnClickListener {
//            val post = collectData()
//            this.findNavController()
//                .navigate(AddPostFragmentDirections.actionAddPostFragmentToDetalsFragment(post))
//        }

        binding.addPostBtn.setOnClickListener {
            //  val post = collectData()
            if(!viewModel.isInternetConnected(requireContext())){
                Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_LONG).show()
            }else{
                viewModel.uploadPostImage(uri!!)
                binding.addingPostProgres.visibility= View.VISIBLE
                binding.addPostBtn.isClickable = false
                binding.previewBtn.isClickable = false
            }
        }

        viewModel.task.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(!viewModel.isInternetConnected(requireContext())){
                    Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_LONG).show()
                }else{
                    viewModel.addPost(collectData(viewModel.task.value!!))
                }
            }
        })
        viewModel.postRes.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.addingPostProgres.visibility= View.GONE
            }
        })

        return binding.root
    }

    private fun collectData(uri:String): PostClass {
        val cat = when (binding.catGrop.checkedRadioButtonId) {
            R.id.care_btn -> requireContext().resources.getString(R.string.care)

            R.id.treat_btn -> requireContext().resources.getString(R.string.treatment)

            else -> requireContext().resources.getString(R.string.landscape)
        }
        val post = PostClass(
            binding.titelTxt.text.toString(),
            uri,
            cat,
            "",
            binding.postContant.text.toString(),
            ""
        )
        return post
    }
    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == AppCompatActivity.RESULT_OK) {
                if (result != null) {
                    uri = result.uri //path of image in phone
                    Glide.with(this.requireContext()).load(uri).into(binding.selectedImv)
                }
            }
        }
    }
}