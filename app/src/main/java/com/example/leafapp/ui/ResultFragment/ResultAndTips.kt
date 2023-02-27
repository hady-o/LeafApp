package com.example.leafapp.ui.ResultFragment

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.leafapp.Constants
import com.example.leafapp.R
import com.example.leafapp.SharedPref
import com.example.leafapp.databinding.FragmentResultAndTipsBinding
import com.squareup.moshi.Json
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
        viewModel.context = requireContext()
        viewModel.resources = resources
        viewModel.activity = requireActivity()
        val args = arguments?.let { ResultAndTipsArgs.fromBundle(it) }
        args?.let {
            img = it.myImage
            isSave = it.saveImge
            prediction = it.prediction
        }

        binding.backBtn.setOnClickListener() {
            if(SharedPref.fromWhereToResults.equals(Constants.HISTORY,true))
                this.findNavController().navigate(ResultAndTipsDirections.actionResultAndTips2ToHomeFragment(0))
            else
                this.findNavController().navigate(ResultAndTipsDirections.actionResultAndTips2ToHomeFragment(1))
        }

        viewModel.loading.observe(viewLifecycleOwner){ isloading ->
            if(isloading)
            {
                binding.noPlant.visibility = View.GONE
                binding.plant.visibility = View.GONE
                binding.resLv.visibility = View.GONE
                binding.fail.visibility = View.GONE
                binding.loading.visibility = View.VISIBLE
            }
            else
            {
                binding.loading.visibility = View.GONE
                binding.noPlant.visibility = View.VISIBLE
                binding.plant.visibility = View.VISIBLE
                binding.resLv.visibility = View.VISIBLE
                binding.fail.visibility = View.VISIBLE
            }
        }

        viewModel.diseaseNameLD.observe(viewLifecycleOwner, Observer {
            binding.diseaseText.text = it
            if (it.equals(getString(R.string.healthy),true)) {
                binding.statImg.setImageResource(R.drawable.good_plant)
            }
        })

        viewModel.plantNameLD.observe(viewLifecycleOwner, Observer {
            if(it != null){
                binding.plantName.text = it
                binding.noPlant.visibility = View.GONE
            }
        })

        viewModel.diseasData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.disease = it
                binding.fail.visibility = View.GONE
            } else {
                binding.resLv.visibility = View.GONE
            }
        })
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
            /*if (viewModel.isPlant(image)) {
                binding.noPlant.visibility = View.GONE
                viewModel.predictImage(image, isSave, prediction)


            } else {
                binding.plant.visibility = View.GONE
                binding.resLv.visibility = View.GONE
            }*/
            val plantClass = viewModel.onlinePredictImage(image, isSave, prediction)
            if(plantClass != Constants.NOT_A_PLANT)
                binding.noPlant.visibility = View.GONE
            else
            {
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

}