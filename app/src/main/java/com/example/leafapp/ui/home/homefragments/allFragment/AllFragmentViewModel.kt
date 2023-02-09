package com.example.leafapp.ui.home.homefragments.allFragment

import android.app.Application
import androidx.lifecycle.*
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.posts.PostDao
import com.example.leafapp.posts.PostsRepoImpl
import kotlinx.coroutines.launch

class AllFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val database = PostDao.PostRoomDataBase.getInstance(application)
    private val repo = PostsRepoImpl(database, application.applicationContext)
    private val _allPosts = MutableLiveData<List<PostClass>>()
    val allPosts: LiveData<List<PostClass>> = _allPosts


    init {
        refresh()
        getAllPost()
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                repo.refreshData()
            } catch (e: Exception) {
            }
        }
    }

    fun getAllPost() {
        viewModelScope.launch {
            _allPosts.value = database.dao.getAllPosts()
        }
    }

    fun getPost(type: String) {
        viewModelScope.launch {
            _allPosts.value = database.dao.getSomePosts(type)
        }
    }
}