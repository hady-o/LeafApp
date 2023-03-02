package com.example.leafapp.ui.home.homemenus


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.leafapp.Constants
import com.example.leafapp.SharedPref
import com.example.leafapp.adapters.PlantAdapter
import com.example.leafapp.databinding.FragmentHistoryBinding
import com.example.leafapp.dataclass.PlantClass
import com.example.leafapp.ui.home.HomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList


class HistoryFragment : Fragment() {
    private val REQUEST_CODE_SPEECH_INPUT = 1
    private var plants: MutableList<PlantClass> = ArrayList()
    lateinit var binding : FragmentHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = FragmentHistoryBinding.inflate(layoutInflater)
        val adapter = PlantAdapter(PlantAdapter.HistoryListenerClass {
            SharedPref.fromWhereToResults= Constants.HISTORY
            this.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToResultAndTips2(
                        it.photo,
                        false,
                        "${it.name}___${it.disease.replace(' ', '_')}"
                    )
                )
        })

        binding.searchUserScans.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(currentText: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(currentText: CharSequence?,  start: Int, before: Int, count: Int) {
                    val newPlants: MutableList<PlantClass> = ArrayList()

                    Log.i("oooo", " ${plants.count()}")
                    plants.forEach { userPlant ->
                        if(userPlant.name.contains(currentText.toString(), ignoreCase = true)
                            || userPlant.disease.contains(currentText.toString(), ignoreCase = true))
                            newPlants.add(userPlant)
                    }
                    adapter.submitList(newPlants)
                    binding.res.adapter = adapter
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            }
        )

        FirebaseFirestore.getInstance()
            .collection("history")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("photos")
            .addSnapshotListener { value, _ ->
                plants.clear()
                for (document in value!!) {
                    val tmp = document.getString("className").toString()
                    val l = tmp.split("___")
                    val plantName = l[0]
                    val disease = l[1].replace('_', ' ')
                    val plant = PlantClass(
                        document.id, plantName, disease,
                        document.getString("date").toString(),
                        document.getString("photoUri").toString()
                    )
                    plants.add(plant)
                }
                adapter.submitList(plants)
            }
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
        binding.res.adapter = adapter
        return binding.root
    }
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                binding.searchUserScans.setText(
                    Objects.requireNonNull(result)!!.get(0)
                )
            }
        }
    }
}