package com.example.leafapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.leafapp.databinding.FragmentAddPostBinding
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.notification.MyFirebaseMessagingService
import com.shashank.sony.fancytoastlib.FancyToast
import com.theartofdev.edmodo.cropper.CropImage
import io.noties.markwon.Markwon
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.MarkwonEditorTextWatcher
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.ImagesPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin

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

        binding.previewBtn.setOnClickListener {
            val post = collectData()
            this.findNavController()
                .navigate(AddPostFragmentDirections.actionAddPostFragmentToDetalsFragment(post))
        }

        binding.addPostBtn.setOnClickListener {
            val post = collectData()
            viewModel.uploadPost(requireActivity())
            binding.addingPostProgres.visibility= View.VISIBLE
            binding.addPostBtn.isClickable = false
            binding.previewBtn.isClickable = false
        }

        viewModel.uri.observe(viewLifecycleOwner, Observer {
            it?.let {
                Glide.with(this.requireContext()).load(it).into(binding.selectedImv)

            }
        })

        viewModel.uplodingIsDone.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it)
                    FancyToast.makeText(
                        requireContext(),
                        "The Post have uploded successfully",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,
                        true
                    ).show()
            }
            binding.addingPostProgres.visibility = View.GONE
            binding.addPostBtn.isClickable = true
            binding.previewBtn.isClickable = true
            var note= MyFirebaseMessagingService()
            note.sendNotification("new post uploaded",requireActivity())
        })

        // obtain Markwon instance
        val markwon: Markwon = Markwon.create(this.requireContext())

        // create editor
        val editor: MarkwonEditor = MarkwonEditor.create(markwon)

        // set edit listener
        binding.postContant.addTextChangedListener(MarkwonEditorTextWatcher.withProcess(editor));


        return binding.root
    }

    private fun collectData(): PostClass {
        val cat = when (binding.catGrop.checkedRadioButtonId) {
            R.id.care_btn -> requireContext().resources.getString(R.string.care)

            R.id.treat_btn -> requireContext().resources.getString(R.string.treatment)

            else -> requireContext().resources.getString(R.string.landscape)
        }
        val post = PostClass(
            binding.titelTxt.text.toString(),
            viewModel.uri.value.toString(),
            cat,
            "",
            binding.postContant.text.toString(),
            ""
        )
        viewModel.setPost(post)
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
                    viewModel.setUri(uri)

                }
            }
        }
    }


}