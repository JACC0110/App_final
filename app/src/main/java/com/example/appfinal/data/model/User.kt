package com.example.appfinal.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val names: String,
    val lastnames: String,
    val avatar: String? = null,
    val first_login: Boolean? = null
) : Parcelable

