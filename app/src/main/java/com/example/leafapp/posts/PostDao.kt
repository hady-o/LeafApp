package com.example.leafapp.posts

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.leafapp.dataclass.PostClass
import kotlinx.coroutines.flow.Flow


@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserPosts(post : PostClass)


    @Query("select * from PostClass")
    fun getAllPosts():List<PostClass>

    @Query("SELECT * from PostClass where type = :typee")
    fun getSomePosts(typee:String): List<PostClass>

//    @Query("update PostClass SET likeCount=:likes WHERE title = :titlee")
//    fun updatePost(titlee:String,likes:Int)




    @Database(entities = [PostClass::class], version = 2, exportSchema = false)
    abstract class PostRoomDataBase: RoomDatabase() {
        abstract val dao : PostDao

        companion object {

            @Volatile
            private var Instance: PostRoomDataBase? = null

            fun getInstance(context: Context): PostRoomDataBase {

                synchronized(this)
                {
                    var instance = Instance
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            PostRoomDataBase::class.java,
                            "asteroid_database"
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                        Instance = instance
                    }
                    return instance
                }

            }
        }
    }

}