package com.example.leafapp.posts

import android.content.Context
import android.widget.Toast
import com.example.leafapp.dataclass.PlantClass
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsRepoImpl(val dataBase: PostDao.PostRoomDataBase,var context: Context) : PostsRepo {
    override suspend fun refreshData() {

        withContext(Dispatchers.IO){
            var allPosts : MutableList<PostClass> = ArrayList()
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
                            document.getString("doc")!!
                        )
                        allPosts.add(p)
//                        FancyToast.makeText(context,document.getString("title")!!,FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
////                        FancyToast.makeText(context,)
                        dataBase.dao.inserPosts(p)
                    }

                }

        }

    }


    override suspend fun addPost(post: PostClass) {
        Firebase.firestore.collection("Posts")
            .add(post).addOnCompleteListener(){
                val info = hashMapOf(
                    "likes" to 0,
                    "shares" to 0,
                )
                Firebase.firestore.collection("postInfo")
                    .document(it.getResult().id).set(info)
            }
    }


    override suspend fun updateLikes(id:String,likes:Int) {
        Firebase.firestore.collection("postInfo")
            .document(id).update("likes",likes)
    }
}