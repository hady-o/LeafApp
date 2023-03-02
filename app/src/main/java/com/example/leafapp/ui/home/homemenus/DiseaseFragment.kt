package com.example.leafapp.ui.home.homemenus

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.leafapp.Constants
import com.example.leafapp.DiseasesData
import com.example.leafapp.SharedPref
import com.example.leafapp.adapters.DiseaseAdapter
import com.example.leafapp.bindDisease
import com.example.leafapp.databinding.FragmentDiseaseBinding
import com.example.leafapp.dataclass.DiseaseClass
import com.example.leafapp.ui.home.HomeFragmentDirections
import java.util.*
import kotlin.collections.ArrayList


class DiseaseFragment : Fragment() {
    private val REQUEST_CODE_SPEECH_INPUT = 1
    lateinit var binding:FragmentDiseaseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiseaseBinding.inflate(layoutInflater)
        val adapter= DiseaseAdapter(DiseaseAdapter.DiseaseListenerClass {
            SharedPref.fromWhereToResults=Constants.DISEASE
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToResultAndTips2("",false,it.diseaseName))
        })
        adapter.submitList(DiseasesData.lookUpList)

        binding.searchBtnDisease.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val tmpList: MutableList<DiseaseClass> = ArrayList()

                DiseasesData.lookUpList.forEach { diseaseClass ->
                    if(diseaseClass.diseaseName.contains(s.toString(), ignoreCase = true))
                        tmpList.add(diseaseClass)
                }
                adapter.submitList(tmpList)

            }
        })
        binding.mic.setOnClickListener(){
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
//            intent.putExtra(
//                RecognizerIntent.EXTRA_LANGUAGE,
//              Locale.
//            )
            if(SharedPref.language.equals(Constants.ARABIC, true))
            {
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ar");
            }
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast
                    .makeText(
                        requireContext(), " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
        binding.allRC.adapter = adapter
        return binding.root
    }
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                binding.searchBtnDisease.setText(
                    Objects.requireNonNull(result)!!.get(0)
                )
            }
        }
    }
}