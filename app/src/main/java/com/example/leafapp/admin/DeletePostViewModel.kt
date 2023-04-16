package com.example.leafapp.admin

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.posts.PostDao
import com.example.leafapp.posts.PostsRepoImpl
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.launch

class DeletePostViewModel (application: Application) : AndroidViewModel(application) {
    private val database = PostDao.PostRoomDataBase.getInstance(application)
    private val repo = PostsRepoImpl(database, application.applicationContext)
    private val _deletePostRes = MutableLiveData<DocumentReference>()
    val deletePostRes : LiveData<DocumentReference>
        get() = _deletePostRes


    fun deletePost(post: PostClass){
        viewModelScope.launch {
            repo.deletePost(post)
        }
    }

    fun addDeletedPost(doc:String){
        viewModelScope.launch {
            _deletePostRes.value = repo.addDeletedPost(doc)
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