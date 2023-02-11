package com.example.leafapp.ui.home.homemenus


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.leafapp.adapters.PlantAdapter
import com.example.leafapp.databinding.FragmentHistoryBinding
import com.example.leafapp.dataclass.PlantClass
import com.example.leafapp.ui.home.HomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore



class HistoryFragment : Fragment() {
    var plants: MutableList<PlantClass> = ArrayList<PlantClass>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHistoryBinding.inflate(layoutInflater)
        val adapter = PlantAdapter(PlantAdapter.HistoryListenerClass {
            this.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToResultAndTips2(
                        it.photo,
                        false,
                        "${it.name}___${it.disease.replace(' ', '_')}"
                    )
                )
        })

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
                    val dseas = l[1].replace('_', ' ')
                    val plant = PlantClass(
                        document.id, plantName, dseas,
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