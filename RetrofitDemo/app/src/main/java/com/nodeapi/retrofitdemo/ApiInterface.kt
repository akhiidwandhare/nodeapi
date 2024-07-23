package com.nodeapi.retrofitdemo

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST("api/login")
    fun userLogin(@Body requestBody: RequestBody): Call<User>

    @GET("profile")
    fun getUser(): Call<User>
}