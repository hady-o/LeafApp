package com.example.leafapp

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.*
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.posts.PostDao
import com.example.leafapp.posts.PostsRepoImpl
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.io.ByteArrayOutputStream

class AddPostViewModel (application: Application) : AndroidViewModel(application) {
    private val database = PostDao.PostRoomDataBase.getInstance(application)
    private val repo = PostsRepoImpl(database, application.applicationContext)
    private val _task = MutableLiveData<String>()
    val task : LiveData<String>
        get() = _task

    private val _postRes = MutableLiveData<DocumentReference>()
    val postRes : LiveData<DocumentReference>
        get() = _postRes


    fun uploadPostImage(imageUri:Uri){
        viewModelScope.launch {
            _task.value = repo.uploadPostPhoto(imageUri)
        }
    }

    fun addPost(post:PostClass){
        viewModelScope.launch {
            _postRes.value = repo.addPost(post)
        }
    }


    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

}