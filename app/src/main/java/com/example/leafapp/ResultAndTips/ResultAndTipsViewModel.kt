package com.example.leafapp.ResultAndTips

import android.content.ContentResolver
import android.content.res.AssetManager
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leafapp.savePhoto
import java.text.SimpleDateFormat
import java.util.*

class ResultAndTipsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var imgBitMab : Bitmap? = null
//    private lateinit var pridiction  : MutableLiveData<String>
    private lateinit var pridiction: String
    public lateinit var plantName :String
    public lateinit var dseas:String




    fun setImageBitmab(imgUri: Bitmap?, assest: AssetManager, contentResolver: ContentResolver){
        imgBitMab = imgUri
        ImageClassifier.init(assest, "Models/MobileNetV2/MobileNetV2.tflite")


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
        pridiction= ImageClassifier.labes[idx]
//        pridiction.value= ImageClassifier.labes[idx]
        var l = pridiction.split("___")
        plantName = l[0]
        dseas = l[1].replace('_',' ')
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())

        savePhoto(imgUri!!, currentDate,pridiction )
    }
//    fun getPridction () = pridiction as LiveData<String>




}