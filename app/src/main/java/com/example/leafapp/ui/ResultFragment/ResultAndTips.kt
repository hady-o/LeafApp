package com.example.leafapp.ui.ResultFragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.leafapp.Constants
import com.example.leafapp.R
import com.example.leafapp.SharedPref
import com.example.leafapp.databinding.FragmentResultAndTipsBinding
import com.example.leafapp.dataclass.DiseaseClass
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.ui.home.homemenus.DiseaseFragment
import com.google.common.reflect.Reflection.getPackageName
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.ImagesPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin

class ResultAndTips : Fragment() {


    private lateinit var viewModel: ResultAndTipsViewModel
    lateinit var binding: FragmentResultAndTipsBinding

    private lateinit var img: String
    var isSave: Boolean = true
    var prediction: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultAndTipsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ResultAndTipsViewModel::class.java]
        val markwon: Markwon =
            Markwon.builder(requireContext()) // automatically create Glide instance
                .usePlugin(ImagesPlugin.create())
                .usePlugin(GlideImagesPlugin.create(requireContext())) // use supplied Glide instance
                .usePlugin(GlideImagesPlugin.create(Glide.with(requireContext())))
                .usePlugin(HtmlPlugin.create())
                .build()
        viewModel.context = requireContext()
        viewModel.resources = resources
        viewModel.activity = requireActivity()
        binding.disease = DiseaseClass(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
        val args = arguments?.let { ResultAndTipsArgs.fromBundle(it) }
        args?.let {
            img = it.myImage
            isSave = it.saveImge
            prediction = it.prediction
        }

        binding.youTubePlayerView.enterFullScreen()
        binding.youTubePlayerView.toggleFullScreen()
        lifecycle.addObserver(binding.youTubePlayerView)
        binding.youTubePlayerView.getPlayerUiController()



        binding.backBtn.setOnClickListener() {
            if(SharedPref.fromWhereToResults.equals(Constants.HISTORY,true))
                this.findNavController().navigate(ResultAndTipsDirections.actionResultAndTips2ToHomeFragment(0))
            else
                this.findNavController().navigate(ResultAndTipsDirections.actionResultAndTips2ToHomeFragment(1))
        }

        viewModel.diseaseNameLD.observe(viewLifecycleOwner, Observer {
            binding.disease?.diseaseName = it
            if (it.equals(getString(R.string.healthy),true)) {
                binding.statImg.setImageResource(R.drawable.good_plant)
            }
        })

        viewModel.plantNameLD.observe(viewLifecycleOwner, Observer {
            if(it != null){
                binding.disease?.plantName = it
                binding.noPlant.visibility = View.GONE
            }
        })

        viewModel.diseasData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.disease = it
                binding.fail.visibility = View.GONE
                markwon.setMarkdown(binding.resMakrdownTxt,it.symptoms)

            } else {
                binding.resLv.visibility = View.GONE
            }
        })

        binding.shareBtn.setOnClickListener(){
            val share = Intent(Intent.ACTION_SEND)

            share.putExtra(Intent.EXTRA_TEXT,
                "Plant name: "+viewModel.plantNameLD.value+"\n\n\n"
                        +"Disease name: "+viewModel.diseaseNameLD.value+"\n\n\n"
                        +"Treatment:"+"\n"
            +viewModel.diseasData.value!!.tips)
            share.type = "text/*"
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            binding.root.context.startActivity(share)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var image = BitmapFactory.decodeResource(resources, android.R.drawable.ic_menu_camera)

        // Means that the person scan the image for the fires time
        if (isSave) {
            image = MediaStore.Images.Media.getBitmap(
                this.requireActivity().contentResolver,
                Uri.parse(img)
            )
            binding.takenImg.setImageBitmap(image)
            if (viewModel.isPlant(image)) {
                binding.noPlant.visibility = View.GONE
                viewModel.predictImage(image, isSave, prediction)


            } else {
                binding.plant.visibility = View.GONE
                binding.resLv.visibility = View.GONE
            }
        }
        // Means the person is just checking the image from the history fragment
        else {
            try{
                Glide.with(requireContext())
                    .load(img)
                    .into(binding.takenImg)
                prediction?.let { viewModel.getDiseaseData(it) }
            }catch (ex:java.lang.IndexOutOfBoundsException){
                ex.message.toString()
            }

        }


    }

    private fun getColored(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }
}