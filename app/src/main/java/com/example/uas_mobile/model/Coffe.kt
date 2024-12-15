package com.example.uas_mobile.model

import com.google.gson.annotations.SerializedName

data class Coffe(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("imageUrl")
    val imageURL: String
)
