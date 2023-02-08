package com.example.leafapp.dataclass

class PostClass(
    var postId: Int,
    var title: String,
    var likeCount: Int,
    var shareCount: Int,
    var photo: String,
    var type: String,
    var topics: List<String>,
    var contents: List<String>
) {

    fun likePressed(){
        likeCount+=1
    }

    fun likeRelased(){

        if(likeCount>0)
        {
            likeCount -=1
        }
    }

    fun sharePressed(){
        shareCount+=1
    }

    fun shareRelased(){

        if(shareCount>0)
        {
            shareCount -=1
        }
    }

}