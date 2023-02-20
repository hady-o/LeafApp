package com.example.leafapp.history

import com.example.leafapp.dataclass.PlantClass

interface HistoryRepo {
    suspend fun addPlant(plant:PlantClass)
    suspend fun getAllPlants()
}