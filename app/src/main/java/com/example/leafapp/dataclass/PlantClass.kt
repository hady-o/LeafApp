package com.example.leafapp.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlantClass(@PrimaryKey var id:String,var name:String,var disease:String,var date:String,var photo:String) {

}