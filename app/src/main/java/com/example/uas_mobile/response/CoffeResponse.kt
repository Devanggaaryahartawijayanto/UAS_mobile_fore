package com.example.uas_mobile.response

data class CoffeResponse(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val price: Int,

    val success: Boolean,
    val message: String,
)
