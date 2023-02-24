package com.example.leafapp

import android.app.Activity
import android.media.MediaCodec
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
import com.shashank.sony.fancytoastlib.FancyToast

fun validatSignUp(name: EditText, email: EditText, password: EditText,activity: Activity):Boolean {
    val userName = name.text.toString()
    val userEmail = email.text.toString().trim { it <= ' ' }
    val userPassword = password.text.toString().trim { it <= ' ' }
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
    val passwordMatcher = Regex(passwordPattern)

    if (userName.isEmpty()) {
        name.error = "Name is invalid"
        return false
    }
    else  if (userEmail.isEmpty()) {
        email.error = "Email is invalid"
        return false
    }
    else  if (userPassword.isEmpty()) {
        password.error = "Password is invalid"
        return false
    }
    else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
        email.error = "Email must be like something@example.com"
        email.requestFocus()
        return false
    }
    else if (passwordMatcher.find(userPassword)==null) {
        password.error = "pass must contains at least 1 digit,1 lower and upper letter and 8 character"
        FancyToast.makeText(activity,"pass must contains at least 1 digit,1 lower and upper letter and 8 character",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show()
        email.requestFocus()
        return false
    }
    else return true
}


fun resetPassword(email: EditText?, activity: Activity)
{
    var mail = email!!.text.toString()

    if (mail.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
        email!! .error = "Email is invalid"
    } else {
        FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity,"E-mail has been sent",Toast.LENGTH_LONG).show()
                }
            }
    }
}

var isShow = true
fun showPassword(pass: EditText?) {
    if (isShow) {
        pass!!.transformationMethod = null
        isShow = false
    } else {
        pass!!.transformationMethod = PasswordTransformationMethod()
        isShow = true
    }
}