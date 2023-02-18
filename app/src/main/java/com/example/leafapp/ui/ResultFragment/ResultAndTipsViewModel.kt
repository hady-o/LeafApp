package com.example.leafapp.ui.ResultFragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafapp.DiseasesData
import com.example.leafapp.dataclass.DiseaseClass
import com.example.leafapp.savePhoto
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ResultAndTipsViewModel : ViewModel() {
    //    private var imgbitmap: Bitmap? = null
    val TAG = "ResultClass"
    private lateinit var pridiction: String

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    @SuppressLint("StaticFieldLeak")
    lateinit var activity: Activity

    lateinit var resources: android.content.res.Resources
    private val _plantNameLD = MutableLiveData<String>()
    val plantNameLD: LiveData<String>
        get() = _plantNameLD

    private val _diseasNamaLD = MutableLiveData<String>()
    val diseaseNameLD: LiveData<String>
        get() = _diseasNamaLD

    var diseaseData: DiseaseClass? = null

    fun isPlant(imageBitmap: Bitmap?, asset: AssetManager): Boolean {
        var isItPlant = false
        val isPlant = ImageClassifier(asset, "Models/IsPlant/isPlantModel.tflite")
        isPlant.INPUT_SIZE = 224
        val isPlantRes = isPlant.predictIsPlant(imageBitmap)
        if (isPlantRes == 2101) {
            isItPlant = false
            FancyToast.makeText(
                context,
                "That is Not Plant Tho But Ok",
                FancyToast.LENGTH_LONG,
                FancyToast.WARNING,
                true
            ).show()
        } else {
            isItPlant = true
            FancyToast.makeText(
                context,
                "That is a plant",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                true
            ).show()
        }
        return isItPlant
    }

    fun setBitmap(
        imageBitmap: Bitmap?,
        asset: AssetManager,
        isSave: Boolean,
        prediction: String?
    ) {
        viewModelScope.launch {

            val mainModel = ImageClassifier(asset, "Models/MobileNetV2/MobileNetV2.tflite")
            mainModel.INPUT_SIZE = 256
            if (prediction == null) {
                var res =
                    mainModel.predict(
                        imageBitmap
                    )
                var max = 0f
                var idx = 0
                for (i in 0..59) {
                    if (i == 0) {
                        max = res.get(i).toFloat()
                    } else {
                        if (max <= res.get(i)) {
                            max = res.get(i).toFloat()
                            idx = i
                        }
                    }
                }
                pridiction = DiseasesData.labes[idx]
            } else {
                pridiction = prediction
            }
            diseaseData = DiseasesData.lookUp[pridiction]

            val l = pridiction.split("___")
            _plantNameLD.value = l[0]
            _diseasNamaLD.value = l[1].replace('_', ' ')
            if (isSave) {
                // Saving The Photo to the fb
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val currentDate = sdf.format(Date())
                savePhoto(imageBitmap!!, currentDate, pridiction)
            }
        }
    }
}