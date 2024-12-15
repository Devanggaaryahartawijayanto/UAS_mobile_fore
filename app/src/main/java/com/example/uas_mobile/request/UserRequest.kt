package com.example.uas_mobile.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("role")
    val role:String
)
