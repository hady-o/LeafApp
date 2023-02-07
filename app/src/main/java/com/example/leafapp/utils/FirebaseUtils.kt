package com.example.leafapp.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await():T{
    return suspendCancellableCoroutine {con ->
        addOnCompleteListener(){
            if (it.exception!=null){
                con.resumeWithException(it.exception!!)
            }else{
                con.resume(it.result,null)
            }
        }
    }
}