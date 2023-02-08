package com.example.leafapp.homefragments.allFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leafapp.R
import com.example.leafapp.dataclass.PostClass

class AllFragmentViewModel:ViewModel() {
    val allPosts = MutableLiveData<List<PostClass>>()
    var ll = listOf<PostClass>()

    private val _currPost = MutableLiveData<PostClass?>()
    val currPost : MutableLiveData<PostClass?>
        get() = _currPost


    private val post = PostClass(1,"post 1",12,123, R.drawable.co,"#LandScacap", listOf("topic 1"),
        listOf("sup 1 "))

    init {
        allPosts.value = listOf<PostClass>(post,post,post,post,post)
//        allPosts.value?.add(post)
//        allPosts.value?.add(post)
//        allPosts.value?.add(post)
//        allPosts.value?.add(post)
//        allPosts.value?.add(post)
    }

    fun startNav(selected: PostClass){
        _currPost.value = selected
    }
    fun endNav(){
        _currPost.value = null
    }
}