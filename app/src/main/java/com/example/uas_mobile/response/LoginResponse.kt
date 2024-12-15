package com.example.uas_mobile.response

import android.os.Message

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val role: String
)
