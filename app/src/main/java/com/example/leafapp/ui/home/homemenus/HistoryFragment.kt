package com.example.leafapp.ui.home.homemenus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.leafapp.adapters.PlantAdapter
import com.example.leafapp.databinding.FragmentHistoryBinding
import com.example.leafapp.dataclass.PlantClass
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
        var adapter = PlantAdapter(plants)
        binding.res.adapter = adapter

        FirebaseFirestore.getInstance()
            .collection("history")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("photos")
            .addSnapshotListener{
                    value, _ ->
                plants.clear()
                for (document in value!!) {
                    var tmp = document.getString("className").toString()
                    var l = tmp.split("___")
                    var plantName = l[0]
                    var dseas = l[1].replace('_',' ')
                var plant = PlantClass( document.id,plantName,dseas,
                                        document.getString("date").toString(),
                                        document.getString("photoUri").toString()
                                        )
                plants.add(plant)
            }
                adapter.notifyDataSetChanged()
            }
        return binding.root
    }
}