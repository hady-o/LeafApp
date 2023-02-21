package com.example.leafapp

import android.content.res.AssetManager
import com.example.leafapp.dataclass.DiseaseClass
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStreamReader

object DiseasesData {
    val lookUp: HashMap<String, DiseaseClass?> = hashMapOf("key" to null)
    val lookUpList = ArrayList<DiseaseClass>()
    val lookUpList2 = ArrayList<DiseaseClass>()
    val labes = arrayOf(
        "Alstonia Scholaris___diseased",
        "Alstonia Scholaris___healthy",
        "Apple___Apple_scab",
        "Apple___Black_rot",
        "Apple___Cedar_apple_rust",
        "Apple___healthy",
        "Arjun___diseased",
        "Arjun___healthy",
        "Bael___diseased",
        "Basil___healthy",
        "Blueberry___healthy",
        "Cherry_(including_sour)___healthy",
        "Cherry_(including_sour)___Powdery_mildew",
        "Chinar ___diseased",
        "Chinar ___healthy",
        "Corn_(maize)___Cercospora_leaf_spot Gray_leaf_spot",
        "Corn_(maize)___Common_rust_",
        "Corn_(maize)___healthy",
        "Corn_(maize)___Northern_Leaf_Blight",
        "Gauva___diseased",
        "Gauva___healthy",
        "Grape___Black_rot",
        "Grape___Esca_(Black_Measles)",
        "Grape___healthy",
        "Grape___Leaf_blight_(Isariopsis_Leaf_Spot)",
        "Jamun___diseased",
        "Jamun___healthy",
        "Jatropha___diseased",
        "Jatropha___healthy",
        "Lemon___diseased",
        "Lemon___healthy",
        "Mango ___healthy",
        "Mango___diseased",
        "Orange___Haunglongbing_(Citrus_greening)",
        "Peach___Bacterial_spot",
        "Peach___healthy",
        "Pepper,_bell___Bacterial_spot",
        "Pepper,_bell___healthy",
        "Pomegranate___diseased",
        "Pomegranate___healthy",
        "PongamiaPinnata___diseased",
        "PongamiaPinnata___healthy",
        "Potato___Early_blight",
        "Potato___healthy",
        "Potato___Late_blight",
        "Raspberry___healthy",
        "Soybean___healthy",
        "Squash___Powdery_mildew",
        "Strawberry___healthy",
        "Strawberry___Leaf_scorch",
        "Tomato___Bacterial_spot",
        "Tomato___Early_blight",
        "Tomato___healthy",
        "Tomato___Late_blight",
        "Tomato___Leaf_Mold",
        "Tomato___Septoria_leaf_spot",
        "Tomato___Spider_mites Two-spotted_spider_mite",
        "Tomato___Target_Spot",
        "Tomato___Tomato_mosaic_virus",
        "Tomato___Tomato_Yellow_Leaf_Curl_Virus"
    )

    fun loadData(asset: AssetManager) {

        val inputStream = InputStreamReader(asset.open("Data/DiseasesDescription.csv"))

        val reader = BufferedReader(inputStream)
        val csvPars = CSVParser.parse(
            reader,
            CSVFormat.DEFAULT.withIgnoreHeaderCase().withRecordSeparator(',')
        )
        csvPars.forEach {
            it?.let {
                var tmp = DiseaseClass(
                    diseaseName = it[0],
                    symptoms = it[1],
                    cause = it[2],
                    howItStared = it[3],
                    tips = it[4]
                )
                lookUp[it[0]] = tmp
                lookUpList.add(tmp)
            }
        }


    }

}