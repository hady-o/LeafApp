package com.example.leafapp.posts

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.Log
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class PostsRepoImpl(val dataBase: PostDao.PostRoomDataBase,var context: Context) : PostsRepo {
    override suspend fun refreshData() {

        withContext(Dispatchers.IO){

            Firebase.firestore.collection("Posts")
                .get().addOnCompleteListener(){
                    for (document in it.getResult())
                    {
                        var p =PostClass(
                            document.getString("title")!!,
                            document.getString("photo")!!,
                            document.getString("type")!!,
                            "",
                            document.getString("contents")!!,
                            document.id
                        )
                        dataBase.dao.inserPosts(p)
                    }

                }

        }

    }

    override suspend fun refreshDeletedData() {
        withContext(Dispatchers.IO){
            Firebase.firestore.collection("deletedPosts")
                .get()
                .addOnCompleteListener(){
                    for (document in it.getResult())
                    {
                        val postDoc = document.getString("deletedPostDocId")!!
                        try {
                            dataBase.dao.deletePost(postDoc)
                        }catch (e:Exception){
                            continue
                        }
                    }
                }
        }
    }


    override suspend fun addToFav(post: PostClass) {
        withContext(Dispatchers.IO){
            Firebase.firestore.collection("fav")
                .document("${FirebaseAuth.getInstance().currentUser!!.uid}_${post.doc}")
                .set(post)
        }
    }

    override suspend fun deleteFromFav(post: PostClass) {
        withContext(Dispatchers.IO){
            Firebase.firestore.collection("fav")
                .document("${FirebaseAuth.getInstance().currentUser!!.uid}_${post.doc}")
                .delete()
        }
    }

    override suspend fun uploadPostPhoto(imageUri: Uri): String? =
          withContext(Dispatchers.IO){

              val task =  Firebase.storage.getReference("posts/" + System.currentTimeMillis() + ".jpg")
                  .putFile(imageUri).await()

              if (task.task.isSuccessful) {
                  return@withContext task.storage.downloadUrl.await().toString()
              } else {
                  Log.e(TAG, "Error uploading image", task.task.exception)
                  return@withContext "Error uploading image"
              }
        }

    override suspend fun addPost(post: PostClass): DocumentReference =
        withContext(Dispatchers.IO){
            val task = Firebase.firestore.collection("Posts")
                .add(post).await()
                return@withContext task
        }

    override suspend fun deletePost(post: PostClass)  =
        withContext(Dispatchers.IO){
           val task = Firebase.firestore.collection("Posts")
                .document(post.doc).delete().await()
        }

    override suspend fun addDeletedPost(docId: String) : DocumentReference =
        withContext(Dispatchers.IO){
            val doc = HashMap<String,String>()
            doc.put("deletedPostDocId",docId)
            val task = Firebase.firestore.collection("deletedPosts")
                .add(doc).await()
            return@withContext task
        }


}