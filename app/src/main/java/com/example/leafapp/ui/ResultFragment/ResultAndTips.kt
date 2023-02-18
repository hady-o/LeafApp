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
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.leafapp.R
import com.example.leafapp.databinding.FragmentResultAndTipsBinding

class ResultAndTips : Fragment() {


    private lateinit var viewModel: ResultAndTipsViewModel
    lateinit var binding: FragmentResultAndTipsBinding

    lateinit var img: String
    var isSave: Boolean = true
    var prediction: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultAndTipsBinding.inflate(layoutInflater)

        val args = arguments?.let {
            ResultAndTipsArgs.fromBundle(
                it
            )
        }
        args?.let {
            img = it.myImage
            isSave = it.saveImge
            prediction = it.prediction
        }


        binding.backBtn.setOnClickListener() {
            Navigation.findNavController(binding.root).navigateUp()
        }
        return binding.root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ResultAndTipsViewModel::class.java)
        viewModel.context = requireContext()
        viewModel.resources = resources
        viewModel.activity = requireActivity()
        var image = BitmapFactory.decodeResource(resources, android.R.drawable.ic_menu_camera)
        if (isSave) {
            image = MediaStore.Images.Media.getBitmap(
                this.requireActivity().contentResolver,
                Uri.parse(img)
            )
            binding.takenImg.setImageBitmap(image)
        } else {
            Glide.with(requireContext())
                .load(img)
                .into(binding.takenImg)
        }

        if (viewModel.isPlant(image, this.requireActivity().assets)) {
            binding.noPlant.visibility = View.GONE
            binding.plant.visibility = View.VISIBLE

            viewModel.setBitmap(image, this.requireActivity().assets, isSave, prediction)
            viewModel.plantNameLD.observe(viewLifecycleOwner) {
                it?.let {
                    binding.plantName.text = it
                }
            }

            viewModel.diseaseNameLD.observe(viewLifecycleOwner) {
                it?.let {
                    binding.diseaseText.text = it
                }
            }
            if (viewModel.diseaseData != null) {
                binding.disease = viewModel.diseaseData
                binding.fail.visibility = View.GONE
            } else {
                binding.resLv.visibility = View.GONE
            }

            if (viewModel.diseaseNameLD.value.equals("healthy")) {
                binding.statImg.setImageResource(R.drawable.good_plant)
            }

            if (viewModel.diseaseData != null) {
                binding.disease = viewModel.diseaseData
                binding.fail.visibility = View.GONE
            } else {
                binding.resLv.visibility = View.GONE
            }

            if (viewModel.diseaseNameLD.value.equals("healthy")) {
                binding.statImg.setImageResource(R.drawable.good_plant)
            }
        } else {

            binding.noPlant.visibility = View.VISIBLE
            binding.plant.visibility = View.GONE
            binding.fail.visibility = View.VISIBLE
            binding.resLv.visibility = View.GONE



        }



    }

}