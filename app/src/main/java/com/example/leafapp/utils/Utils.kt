package com.example.leafapp.utils

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import com.example.leafapp.SharedPref
import java.util.Locale

fun getIdxOfMax(list: FloatArray):Int{
    var max = 0f
    var idx = 0
    for (i in list.indices) {
        if (i == 0) {
            max = list[i]
        } else {
            if (max <= list[i]) {
                max = list[i]
                idx = i
            }
        }
    }
    return idx
}

fun Activity.setLocale(languageCode: String?){
    SharedPref.language=languageCode!!
    val locale=Locale(languageCode)
    Locale.setDefault(locale)
    val resources:Resources=this.resources
    val config: Configuration = resources.configuration
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.displayMetrics)
    window.decorView.layoutDirection = getResources().configuration.layoutDirection
}