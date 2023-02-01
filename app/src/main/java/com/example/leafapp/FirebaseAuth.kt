package com.example.leafapp

import android.app.Activity
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest


fun signUp(name: EditText, email: EditText, password: EditText, v:View,progressBar:ProgressBar) {
    progressBar.setVisibility(View.VISIBLE)
    val userName = name.text.toString()
    val userEmail = email.text.toString().trim { it <= ' ' }
    val userPassword = password.text.toString().trim { it <= ' ' }
    if (userPassword.isEmpty() || userName.isEmpty() || userEmail.isEmpty()) {
        email.error = "Email is invalid"
        password.error = "Password is invalid"
        name.error = "Name is invalid"
        progressBar.setVisibility(View.GONE)
    } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
        email.error = "Email is invalid"
        email.requestFocus()
        progressBar.setVisibility(View.GONE)
    } else {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().getCurrentUser()
                    if (user != null) {
                        //set user Desplay name
                        val profile = UserProfileChangeRequest.Builder()
                            .setDisplayName(userName)
                            .build()
                        user.updateProfile(profile).addOnCompleteListener {
                            progressBar.setVisibility(View.GONE)
                            Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_loginFragment2)
                        }
                    }
                } else if (task.exception is FirebaseAuthUserCollisionException) {
                    email.error = "this Email is already exist"
                    progressBar.setVisibility(View.GONE)
                } else {
                    progressBar.setVisibility(View.GONE)
                }
            })

    }
}


fun signIn(email: EditText, password: EditText,v:View,progressBar:ProgressBar) {
    progressBar.setVisibility(View.VISIBLE)
    val userEmail = email.text.toString()
    val userPassword = password.text.toString()
    if (userEmail.isEmpty() || userPassword.isEmpty()) {
        email.error = "Email is invalid"
        password.error = "password is invalid"
        progressBar.setVisibility(View.GONE)
    } else {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
            if(task.isSuccessful){
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_homeFragment)
                progressBar.setVisibility(View.GONE)
            }
            else
            {
                progressBar.setVisibility(View.GONE)
                Toast.makeText(v.context,"invalid data",Toast.LENGTH_LONG).show()
            }
        })
    }

}

fun resetPassword(email: EditText,activity: Activity)
{
    var mail = email.text.toString()

    if (mail.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
        email.error = "Email is invalid"
    } else {
        FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity,"E-mail has been send",Toast.LENGTH_LONG).show()
                }
            }
    }
}

var isShow = true
fun showPassword(pass: EditText) {
    if (isShow) {
        pass.transformationMethod = null
        isShow = false
    } else {
        pass.transformationMethod = PasswordTransformationMethod()
        isShow = true
    }
}