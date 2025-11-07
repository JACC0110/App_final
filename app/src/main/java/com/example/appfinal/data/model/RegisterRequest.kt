package com.example.appfinal.data.model

data class RegisterRequest (
    val username: String,
    val names: String,
    val lastnames: String,
    val age: Int,
    val gender: String,
    val password: String,
    val email: String
    )