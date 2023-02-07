package com.example.leafapp

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream


fun savePhoto(image: Bitmap,date:String, classNaem:String){
    val mStorageRef: StorageReference = FirebaseStorage.getInstance()
        .getReference("history/" + System.currentTimeMillis() + ".jpg")
    if (image != null) {
        var bytes = ByteArrayOutputStream()
        image!!.compress(Bitmap.CompressFormat.JPEG,90,bytes)
        var bb = bytes.toByteArray()

        mStorageRef.putBytes(bb).addOnCompleteListener()
        {
            it.addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener()
                {
                    saveHistory(it,date,classNaem)
                }
            }
        }
    }
}
fun saveHistory(uri:Uri,date:String,className:String){
    val photo = hashMapOf(
        "photoUri" to uri,
        "date" to date,
        "userId" to FirebaseAuth.getInstance().currentUser!!.uid,
        "className" to  className
    )
    Firebase.firestore.collection("history")
        .document(FirebaseAuth.getInstance().currentUser!!.uid)
        .collection("photos")
        .add(photo)
        .addOnCompleteListener(){

        }

}
