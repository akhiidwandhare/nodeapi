package com.nodeapi.retrofitdemo

import com.google.gson.annotations.SerializedName

data class User(
    val userId: Int,
    val id: Int,
    val title: String,
    @SerializedName("body")
    val text: String
)