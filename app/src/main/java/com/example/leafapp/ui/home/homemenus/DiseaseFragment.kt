package com.example.leafapp.ui.home.homemenus

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.leafapp.Constants
import com.example.leafapp.DiseasesData
import com.example.leafapp.R
import com.example.leafapp.SharedPref
import com.example.leafapp.adapters.DiseaseAdapter
import com.example.leafapp.adapters.PsAdapter
import com.example.leafapp.databinding.FragmentDiseaseBinding
import com.example.leafapp.databinding.FragmentHistoryBinding
import com.example.leafapp.dataclass.DiseaseClass
import com.example.leafapp.ui.home.HomeFragment
import com.example.leafapp.ui.home.HomeFragmentDirections

class DiseaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDiseaseBinding.inflate(layoutInflater)
        val adapter= DiseaseAdapter(DiseaseAdapter.DiseaseListenerClass {
            SharedPref.fromWhereToResults=Constants.DISEASE
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToResultAndTips2("",false,it.diseaseName))
        })
        val allDiseases = DiseasesData.lookUpList.toMutableList()
        binding.allRC.adapter = adapter
        binding.look = DiseasesData

        binding.searchBtnDisease.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                DiseasesData.lookUpList.clear()

                allDiseases.forEach { diseaseClass ->
                    if(diseaseClass.diseaseName.contains(s.toString(), ignoreCase = true))
                        DiseasesData.lookUpList.add(diseaseClass)
                }

                binding.allRC.adapter = adapter
                binding.look = DiseasesData

            }
        })

        return binding.root
    }
}