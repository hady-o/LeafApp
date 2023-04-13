package com.example.leafapp.admin

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafapp.dataclass.PostClass
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class AddPostViewModel : ViewModel() {
    private val _uri = MutableLiveData<Uri?>()
        val uri : LiveData<Uri?>
            get() = _uri

    private val _imge_bitMap = MutableLiveData<Bitmap?>()
        val imge_bitMap : LiveData<Bitmap?>
            get() = _imge_bitMap

    private val _post = MutableLiveData<PostClass>()
        val post : LiveData<PostClass>
            get() = _post


    private val _uplodingIsDone = MutableLiveData<Boolean>()
        val uplodingIsDone : LiveData<Boolean>
            get() = _uplodingIsDone

    private val _validPost = MutableLiveData<Boolean>()
        val validPost : LiveData<Boolean>
            get() = _validPost

    fun setPost(p : PostClass){
        _post.value = p
    }

    fun setUri(u: Uri?){
        _uri.value = u
    }

    fun uploadPost(act : Activity){
        viewModelScope.launch {
            _imge_bitMap.value = MediaStore.Images.Media.getBitmap(act.contentResolver,_uri.value)
            imge_bitMap.value?.let {
                val img = it
                _post.value?.let {
                    savePhoto(img, it)
                }
            }

        }
    }


    private fun savePhoto(image: Bitmap, p :PostClass){
        val mStorageRef: StorageReference = FirebaseStorage.getInstance()
            .getReference("history/" + System.currentTimeMillis() + ".jpg")
        var bytes = ByteArrayOutputStream()
        image!!.compress(Bitmap.CompressFormat.JPEG,90,bytes)
        var bb = bytes.toByteArray()

        mStorageRef.putBytes(bb).addOnCompleteListener()
        {
            it.addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener()
                {

                        p.photo = it.toString()
                        addPost(p)
                        _uplodingIsDone.value = true
                        _validPost.value = true


                }
            }
        }
    }

    fun addPost(post: PostClass) {
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
}