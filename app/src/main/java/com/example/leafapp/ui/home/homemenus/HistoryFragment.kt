package com.example.leafapp.ui.home.homemenus


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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



class HistoryFragment : Fragment() {
    private var plants: MutableList<PlantClass> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHistoryBinding.inflate(layoutInflater)
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

        binding.res.adapter = adapter
        return binding.root
    }
}