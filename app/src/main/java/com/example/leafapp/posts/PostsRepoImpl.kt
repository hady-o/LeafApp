package com.example.leafapp.posts

import android.content.Context
import android.widget.Toast
import com.example.leafapp.dataclass.PlantClass
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
                            document.get("likeCount").toString().toInt(),
                            document.get("shareCount").toString().toInt(),
                            document.getString("photo")!!,
                            document.getString("type")!!,
                            document.getString("topics")!!,
                            document.getString("contents")!!
                        )
                        allPosts.add(p)
                        Toast.makeText(context,document.getString("title")!!,Toast.LENGTH_LONG).show()
                        dataBase.dao.inserPosts(p)
                    }

                }

        }

    }


    override fun addPost(post: PostClass) {
        Firebase.firestore.collection("Posts")
            .add(post)
    }
}