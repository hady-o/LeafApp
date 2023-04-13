package com.example.leafapp.posts

import android.graphics.Bitmap
import android.net.Uri
import com.example.leafapp.dataclass.PostClass
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.UploadTask

interface PostsRepo {

    suspend fun refreshData()
    suspend fun addPost(post: PostClass): DocumentReference
    suspend fun deletePost(post: PostClass): Boolean
    suspend fun addToFav(post: PostClass)
    suspend fun deleteFromFav(post: PostClass)
    suspend fun uploadPostPhoto(imageUri: Uri): String?
}