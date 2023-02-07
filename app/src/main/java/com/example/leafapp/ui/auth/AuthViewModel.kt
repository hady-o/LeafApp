package com.example.leafapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leafapp.authentication.AuthRepo
import com.example.leafapp.authentication.AuthRepoImpl
import com.example.leafapp.authentication.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(): ViewModel() {
    private val repo = AuthRepoImpl(FirebaseAuth.getInstance())
    private val _loginState = MutableLiveData<Resource<FirebaseUser>?>(null)
     val loginState : LiveData<Resource<FirebaseUser>?> = _loginState

    private val _signUpState = MutableLiveData<Resource<FirebaseUser>?>(null)
     val signUpState : LiveData<Resource<FirebaseUser>?> = _signUpState

    val user :FirebaseUser?
        get() = repo.currentUSer

    init {
        if(repo.currentUSer!=null)
        {
            _loginState.value=Resource.Success(repo.currentUSer!!)
        }
    }

    fun login(email:String, password:String) =
        viewModelScope.launch {
            _loginState.value=Resource.Load
            val res = repo.login(email,password)
            _loginState.value=res
        }


    fun signUp(name:String, email:String, password:String) =
        viewModelScope.launch {
            _signUpState.value=Resource.Load
            val res = repo.signUp(name,email,password)
            _signUpState.value=res
        }

    fun logOut() =
        viewModelScope.launch {

             repo.logOut()
            _signUpState.value=null
            _loginState.value=null
        }
}