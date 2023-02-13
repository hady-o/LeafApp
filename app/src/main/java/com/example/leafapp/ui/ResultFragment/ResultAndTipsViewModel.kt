package com.example.leafapp.ui.ResultFragment

import android.app.Activity
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafapp.DiseasesData
import com.example.leafapp.dataclass.DiseaseClass
import com.example.leafapp.savePhoto
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ResultAndTipsViewModel : ViewModel() {
    //    private var imgbitmap: Bitmap? = null
    private lateinit var pridiction: String
    lateinit var context: Context
    lateinit var activity: Activity
    lateinit var resources: android.content.res.Resources
    private val _plantNameLD = MutableLiveData<String>()
    val plantNameLD: LiveData<String>
        get() = _plantNameLD

    private val _diseasNamaLD = MutableLiveData<String>()
    val diseaseNameLD: LiveData<String>
        get() = _diseasNamaLD

    var diseaseData: DiseaseClass? = null

    fun setBitmap(
        imageBitmap: Bitmap?,
        asset: AssetManager,
        isSave: Boolean,
        prediction: String?
    ) {
        viewModelScope.launch {
            ImageClassifier.init(
                asset,
                "Models/MobileNetV2/MobileNetV2.tflite"
            )

            if (prediction == null) {
                var res =
                    ImageClassifier.predict(
                        imageBitmap
                    )
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
            } else {
                pridiction = prediction
            }
            diseaseData = DiseasesData.lookUp[pridiction]

            var l = pridiction.split("___")
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