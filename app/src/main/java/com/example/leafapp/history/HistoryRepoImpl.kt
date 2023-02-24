package com.example.leafapp.history

import com.example.leafapp.dataclass.PlantClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HistoryRepoImpl(val database: HistoryDao.PlantRoomDatabase) : HistoryRepo {
    override suspend fun addPlant(plant: PlantClass) {

    }

    override suspend fun getAllPlants() {
        FirebaseFirestore.getInstance()
            .collection("history")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("photos")
            .get().addOnCompleteListener{

                for (document in it.result!!) {
                    val tmp = document.getString("className").toString()
                    val l = tmp.split("___")
                    val plantName = l[0]
                    val disease = l[1].replace('_', ' ')
                    val plant = PlantClass(
                        document.id, plantName, disease,
                        document.getString("date").toString(),
                        document.getString("photoUri").toString()
                    )
                    database.dao.addPlant(plant)
                }

            }
    }
}