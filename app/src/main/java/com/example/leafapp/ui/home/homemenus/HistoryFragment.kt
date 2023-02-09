package com.example.leafapp.ui.home.homemenus

import android.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.example.leafapp.adapters.PlantAdapter
import com.example.leafapp.databinding.FragmentHistoryBinding
import com.example.leafapp.dataclass.PlantClass
import com.example.leafapp.ui.home.HomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shashank.sony.fancytoastlib.FancyToast


class HistoryFragment : Fragment() {
    var plants: MutableList<PlantClass> = ArrayList<PlantClass>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHistoryBinding.inflate(layoutInflater)
        val adapter = PlantAdapter(PlantAdapter.HistoryListenerClass {
            var a = 0

//            var img: Bitmap? = null
            var img = BitmapFactory.decodeResource(resources, R.drawable.ic_menu_camera)

            var chacker = img
            while (img == chacker) {
                a +=1
                Glide.with(requireContext())
                    .asBitmap()
                    .load(it.photo)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                        ) {
                            img = resource
                        }
                    })
            }
            /*FancyToast.makeText(
                this.requireContext(),
                "This planet Name is ${it.name} ant take ${a} tims to load",
                FancyToast.LENGTH_SHORT,
                FancyToast.SUCCESS,
                true
            ).show()*/
            this.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToResultAndTips2(img!!, false))

        })

        FirebaseFirestore.getInstance()
            .collection("history")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("photos")
            .addSnapshotListener { value, _ ->
                plants.clear()
                for (document in value!!) {
                    var tmp = document.getString("className").toString()
                    var l = tmp.split("___")
                    var plantName = l[0]
                    var dseas = l[1].replace('_', ' ')
                    var plant = PlantClass(
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