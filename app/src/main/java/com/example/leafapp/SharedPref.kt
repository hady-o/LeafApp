package com.example.leafapp

import com.chibatching.kotpref.KotprefModel

object SharedPref: KotprefModel(){
    var language by stringPref("en")
    var isLanguageSelected by booleanPref(false)
    var isBoardingFinished by booleanPref(false)
    var fromWhere by stringPref("")
}