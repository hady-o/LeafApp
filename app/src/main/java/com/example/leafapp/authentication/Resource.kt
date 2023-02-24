package com.example.leafapp.authentication

sealed class Resource<out R>{
    data class Success<out R>(val result: R): Resource<R>()
    data class Fail(val ex: Exception): Resource<Nothing>()
    object Load: Resource<Nothing>()
}
