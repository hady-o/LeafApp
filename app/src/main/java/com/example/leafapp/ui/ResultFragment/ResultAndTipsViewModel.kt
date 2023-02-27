package com.example.leafapp.ui.ResultFragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafapp.Constants
import com.example.leafapp.DiseasesData
import com.example.leafapp.api.ModelAPI
import com.example.leafapp.dataclass.DiseaseClass
import com.example.leafapp.dataclass.URLRequestClass
import com.example.leafapp.savePhoto
import com.example.leafapp.utils.getIdxOfMax
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
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

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

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

            val mainModel = ImageClassifier(activity.assets, "Models/MobileNetV2/mobile_net_v2_80_class.tflite",256)
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

    // Predict the image with a base 64 format
    fun onlinePredictImage(
        imageBitmap: Bitmap,
        isSave: Boolean,
        prediction: String?
    ): String{
        var predictedClass = ""
        viewModelScope.launch {
            val data = readImageToBase64(imageBitmap)
            try {
                val json = Gson().toJson(
                    URLRequestClass(data)
                )
                _loading.value = true
                predictedClass = prediction ?: ModelAPI.retrofitService.sendImageToAPI(json).predictedPlantClass
                _loading.value = false
                if(predictedClass != Constants.NOT_A_PLANT)
                    saveToHistory(isSave, imageBitmap, predictedClass)
            }
            catch (e: Exception) {
                Log.i("Api","Failed to Classify: ${e.message}")
            }
        }
        return predictedClass
    }

    private fun saveToHistory(
        isSave: Boolean,
        imageBitmap: Bitmap?,
        prediction: String
    ) {
        getDiseaseData(prediction)
        saveImage(isSave, imageBitmap,prediction)
    }

    private fun readImageToBase64(bm: Bitmap): String {
        Log.i("main", "Converting the image to base64")
        // Rescale the image to 256x256 pixels
        val resizedBitmap = Bitmap.createScaledBitmap(bm, 256, 256, true)
        val byteArray = ByteArrayOutputStream()
        resizedBitmap.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            byteArray
        )
        // Converts the image to byte array
        val imageBytes = byteArray.toByteArray()
        // Converts the byte array to base64 string
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    fun getDiseaseData(pridiction: String) {
        _diseasData.value = DiseasesData.lookUp[pridiction]

        val l = pridiction.split("___")
        try{
            _plantNameLD.value = l[0]
            _diseasNamaLD.value = l[1].replace('_', ' ')
        }catch (ex:java.lang.IndexOutOfBoundsException){
            ex.message.toString()
        }

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