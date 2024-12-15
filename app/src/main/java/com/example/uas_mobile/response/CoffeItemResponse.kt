package com.example.uas_mobile.response

import com.google.gson.annotations.SerializedName

data class CoffeItemResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("imageUrl")
    val imageURL: String
)
