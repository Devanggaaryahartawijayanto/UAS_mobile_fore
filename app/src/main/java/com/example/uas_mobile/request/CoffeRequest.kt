package com.example.uas_mobile.request

import com.google.gson.annotations.SerializedName

data class CoffeRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("imageUrl")
    val imageURL: String
)
