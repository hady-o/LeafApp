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
    private lateinit var inputStream:InputStreamReader
    //val lookUpList2 = ArrayList<DiseaseClass>()
    val labels = arrayOf(
        "Alstonia Scholaris___Galls",
        "Alstonia Scholaris___healthy",
        "Apple___Apple_scab",
        "Apple___Black_rot",
        "Apple___Cedar_apple_rust",
        "Apple___healthy",
        "Arjun___Leaf Curl",
        "Arjun___healthy",
        "Bael___leaf spot",
        "Basil___healthy",
        "Blueberry___healthy",
        "Cherry_(including_sour)___Powdery_mildew",
        "Cherry_(including_sour)___healthy",
        "Chinar ___Black_rot",
        "Chinar ___healthy",
        "Corn_(maize)___Cercospora_leaf_spot Gray_leaf_spot",
        "Corn_(maize)___Common_rust_",
        "Corn_(maize)___Northern_Leaf_Blight",
        "Corn_(maize)___healthy",
        "Cotton___Aphids",
        "Cotton___Bacterial blight",
        "Cotton___Healthy",
        "Cotton___Powdery mildew",
        "Cotton___Target spot",
        "Guava___Die Back",
        "Guava___healthy",
        "Grape___Black_rot",
        "Grape___Esca_(Black_Measles)",
        "Grape___Leaf_blight_(Isariopsis_Leaf_Spot)",
        "Grape___healthy",
        "Jamun___Rust",
        "Jamun___healthy",
        "Jatropha___Die Back",
        "Jatropha___healthy",
        "Lemon___Leaf Curl",
        "Lemon___healthy",
        "Mango ___healthy",
        "Mango___Anthracnose",
        "Mango___Bacterial Canker",
        "Mango___Cutting Weevil",
        "Mango___Die Back",
        "Mango___Gall Midge",
        "Mango___Healthy",
        "Mango___Powdery Mildew",
        "Mango___Sooty Mould",
        "Mango___Anthracnose",
        "Orange___Haunglongbing_(Citrus_greening)",
        "Peach___Bacterial_spot",
        "Peach___healthy",
        "Pepper,_bell___Bacterial_spot",
        "Pepper,_bell___healthy",
        "Pomegranate___Powdery_mildew",
        "Pomegranate___healthy",
        "PongamiaPinnata___mosaic_virus",
        "PongamiaPinnata___healthy",
        "Potato___Early_blight",
        "Potato___Late_blight",
        "Potato___healthy",
        "Raspberry___healthy",
        "Soybean___healthy",
        "Squash___Powdery_mildew",
        "Strawberry___Leaf_scorch",
        "Strawberry___healthy",
        "Tomato___Bacterial_spot",
        "Tomato___Early_blight",
        "Tomato___Late_blight",
        "Tomato___Leaf_Mold",
        "Tomato___Septoria_leaf_spot",
        "Tomato___Spider_mites Two-spotted_spider_mite",
        "Tomato___Target_Spot",
        "Tomato___Tomato_Yellow_Leaf_Curl_Virus",
        "Tomato___Tomato_mosaic_virus",
        "Tomato___healthy",
        "coffee___Rust",
        "coffee___healthy",
        "coffee___red_spider_mite",
        "rice___BrownSpot",
        "rice___Healthy",
        "rice___Hispa",
        "rice___LeafBlast"
    )

    fun loadData(asset: AssetManager) {

        inputStream = if(SharedPref.language.equals(Constants.ENGLISH,true))
            InputStreamReader(asset.open("Data/DiseasesDescription.csv"))
        else
            InputStreamReader(asset.open("Data/ArabicDiseasesDescription.csv"))
        val reader = BufferedReader(inputStream)
        val csvPars = CSVParser.parse(
            reader,
            CSVFormat.DEFAULT.withIgnoreHeaderCase().withRecordSeparator(',')
        )
        csvPars.forEach {
            it?.let {
                val tmp = DiseaseClass(
                    id = it[0],
                    plantName = it[1],
                    diseaseName = it[2],
                    symptoms = it[3],
                    cause = it[4],
                    howItStared = it[5],
                    tips = it[6],
                    code = it[6]
                )
                lookUp[it[0]] = tmp
                if(it[0].equals(Constants.ID,true).not())
                    lookUpList.add(tmp)
            }
        }


    }

}