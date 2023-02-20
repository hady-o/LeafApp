package com.example.leafapp.utils

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