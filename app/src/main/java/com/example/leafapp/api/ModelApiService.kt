package com.example.leafapp.api

import com.example.leafapp.Constants
import com.example.leafapp.dataclass.URLResponseClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
interface ModelAPIService {
    @POST("Plant-detection")
    @Headers("Content-Type: application/json")
    suspend fun sendImageToAPI(@Body image: String) : URLResponseClass
}
object ModelAPI{
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.MODEL_BASE_URL)
        .client(okHttpClient)
        .build()
    val retrofitService: ModelAPIService by lazy {
        retrofit.create(ModelAPIService::class.java)
    }
}