package com.example.appfinal.data.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val httpCode: String,
    val user: User? = null
)
