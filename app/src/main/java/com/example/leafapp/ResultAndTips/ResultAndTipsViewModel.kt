package com.example.leafapp.ResultAndTips

import android.content.ContentResolver
import android.content.res.AssetManager
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.leafapp.dataclass.DiseaseClass
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ResultAndTipsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var imgBitMab: Bitmap? = null

    //    private lateinit var pridiction  : MutableLiveData<String>
    private lateinit var pridiction: String
    lateinit var plantName: String
    lateinit var dseas: String
    var disasData : DiseaseClass? = null


    fun setImageBitmab(imgUri: Bitmap?, assest: AssetManager, contentResolver: ContentResolver) {
        imgBitMab = imgUri
        ImageClassifier.init(assest, "Models/MobileNetV2/MobileNetV2.tflite")

        val inputStream = InputStreamReader(assest.open("Data/DiseasesDescription.csv"))
        val reader = BufferedReader(inputStream)

        val csvPars = CSVParser.parse(reader, CSVFormat.DEFAULT.withIgnoreHeaderCase().withRecordSeparator(','))

        val info : MutableList<DiseaseClass> =ArrayList()
        val lookUp : HashMap<String, DiseaseClass?> = hashMapOf("key" to null)

        csvPars.forEach{
            it?.let {
                var tmp = DiseaseClass(
                    diseaseName = it[0],
                    symptoms = it[1],
                    cause = it[2],
                    howItStared = it[3],
                    tips = it[4]
                )
                lookUp[it[0]]=tmp
            }
        }


        var res = ImageClassifier.predict(imgBitMab)
        var max = 0f
        var idx = 0
        for (i in 0..59) {
            if (i == 0) {
                max = res.get(i)
            } else {
                if (max <= res.get(i)) {
                    max = res.get(i)
                    idx = i
                }
            }
        }
        pridiction = ImageClassifier.labes[idx]
        disasData = lookUp[pridiction]

        var l = pridiction.split("___")
        plantName = l[0]
        dseas = l[1].replace('_', ' ')
        // Saving The Photo to the fb
/*        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        savePhoto(imgUri!!, currentDate, pridiction)*/
    }



}