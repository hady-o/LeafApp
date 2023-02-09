package com.example.leafapp.authentication

import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    val currentUSer:FirebaseUser?
    suspend fun login(email:String, password:String): Resource<FirebaseUser>
    suspend fun signUp(name:String, email:String, password:String): Resource<FirebaseUser>
    fun logOut()
}