package com.example.leafapp.ui.ResultFragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafapp.DiseasesData
import com.example.leafapp.dataclass.DiseaseClass
import com.example.leafapp.savePhoto
import com.example.leafapp.utils.getIdxOfMax
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ResultAndTipsViewModel : ViewModel() {


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

    private val _diseasData = MutableLiveData<DiseaseClass>()
        val diseasData : LiveData<DiseaseClass>
            get() = _diseasData


    var diseaseData: DiseaseClass? = null

    fun isPlant(imageBitmap: Bitmap?): Boolean {

        val isPlant = ImageClassifier(activity.assets, "Models/IsPlant/isPlantModel.tflite",224)
        val isPlantRes = isPlant.predictIsPlant(imageBitmap)
        return isPlantRes != 2101

    }

    fun predictImage(imageBitmap: Bitmap?,
        isSave: Boolean,
        prediction: String?
    ) {
        viewModelScope.launch {

            val mainModel = ImageClassifier(activity.assets, "Models/MobileNetV2/MobileNetV2.tflite",256)
            var pridiction = if (prediction == null) {
                val res = mainModel.predict(imageBitmap)
                val idx = getIdxOfMax(res)
                DiseasesData.labes[idx]
            } else {
                prediction
            }
            getDiseaseData(pridiction)
            saveImage(isSave, imageBitmap,pridiction)
        }
    }

    fun getDiseaseData(pridiction: String) {
        _diseasData.value = DiseasesData.lookUp[pridiction]

        val l = pridiction.split("___")
        _plantNameLD.value = l[0]
        _diseasNamaLD.value = l[1].replace('_', ' ')
    }

    private fun saveImage(isSave: Boolean, imageBitmap: Bitmap?,pridiction: String) {
        if (isSave) {
            // Saving The Photo to the fb
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            savePhoto(imageBitmap!!, currentDate, pridiction)
        }
    }
}