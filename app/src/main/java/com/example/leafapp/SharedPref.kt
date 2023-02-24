package com.example.leafapp

import com.chibatching.kotpref.KotprefModel

object SharedPref: KotprefModel(){
    var language by stringPref(Constants.ARABIC)
    var isLanguageSelected by booleanPref(false)
    var isBoardingFinished by booleanPref(false)
    var fromWhereToProfile by stringPref("")
    var fromWhereToResults by stringPref("")
}