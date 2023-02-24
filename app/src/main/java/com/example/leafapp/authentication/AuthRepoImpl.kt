package com.example.leafapp.authentication
import android.widget.Toast
import com.example.leafapp.utils.await
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import javax.inject.Inject

class AuthRepoImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : AuthRepo {
    override val currentUSer: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val res = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            Resource.Success(res.user!!)
        }catch (e:Exception)
        {
            e.printStackTrace()
            Resource.Fail(e)
        }

    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val res = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            res.user!!.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
            Resource.Success(res.user!!)
        }catch (e:Exception)
        {

            e.printStackTrace()
            Resource.Fail(e)
        }
    }

    override fun logOut() {
        firebaseAuth.signOut()
    }
}