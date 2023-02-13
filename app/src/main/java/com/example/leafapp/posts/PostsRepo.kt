package com.example.leafapp.posts

import com.example.leafapp.dataclass.PostClass
import com.google.firebase.auth.FirebaseUser

interface PostsRepo {

    suspend fun refreshData()
    suspend fun addPost(post: PostClass)
    suspend fun updateLikes(id:String,likes:Int)


}