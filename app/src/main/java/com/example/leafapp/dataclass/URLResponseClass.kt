package com.example.leafapp.dataclass

import com.squareup.moshi.Json

data class URLResponseClass(
    @Json(name = "Class Name")
    val predictedPlantClass: String,
    @Json(name = "message")
    val status: String
)