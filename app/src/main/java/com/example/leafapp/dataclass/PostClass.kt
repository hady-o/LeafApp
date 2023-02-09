package com.example.leafapp.dataclass

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.net.URI
@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
@Entity()
data class PostClass(
    @PrimaryKey
    var title: String,
    var likeCount: Int,
    var shareCount: Int,
    var photo: String,
    var type: String,
    var topics: String,
    var contents: String
) : Parcelable {
//
    fun getLikes (): String = likeCount.toString()
    fun getShares (): String = shareCount.toString()
//    fun likePressed(){
//        likeCount+=1
//    }
//
//    fun likeRelased(){
//
//        if(likeCount>0)
//        {
//            likeCount -=1
//        }
//    }
//
//    fun sharePressed(){
//        shareCount+=1
//    }
//
//    fun shareRelased(){
//
//        if(shareCount>0)
//        {
//            shareCount -=1
//        }
//    }
//
}
