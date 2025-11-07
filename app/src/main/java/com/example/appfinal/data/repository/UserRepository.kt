package com.example.appfinal.data.repository

import android.content.Context
import com.example.appfinal.data.network.ApiClient
import com.example.appfinal.data.model.LoginRequest
import com.example.appfinal.data.model.LoginResponse
import com.example.appfinal.data.model.RegisterRequest
import com.example.appfinal.data.model.RegisterResponse
import com.example.appfinal.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val context: Context) {

    private val apiService = ApiClient.service

    fun login(username: String, password: String, callback: (Boolean, String, User?) -> Unit) {
        val request = LoginRequest(username, password)
        val call = apiService.login(request)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val body = response.body()
                if (response.isSuccessful && body != null && body.success && body.user != null) {
                    callback(true, "Inicio de sesión exitoso", body.user)
                } else {
                    callback(false, "Usuario o Contraseña incorrectos", null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(false, "Error de red", null)
            }
        })
    }

    fun register(
        username: String,
        names: String,
        lastnames: String,
        age: Int,
        gender: String,
        password: String,
        email: String,
        callback: (Boolean, String) -> Unit
    ) {
        val request = RegisterRequest(username, names, lastnames, age, gender, password, email)
        val call = apiService.register(request)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    callback(body.success, body.message.ifEmpty { "Error al registrarse" })
                } else {
                    callback(false, "Error al registrarse")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                callback(false, "Error de red")
            }
        })
    }

    fun changePassword(userId: Int, newPassword: String, callback: (Boolean, String) -> Unit) {
        val body = mapOf("password" to newPassword)
        val call = apiService.changePassword(userId, body)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    callback(true, "Contraseña actualizada correctamente")
                } else {
                    callback(false, "Error al cambiar la contraseña")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(false, "Error de red")
            }
        })
    }

    fun updateAvatar(userId: Int, avatarBase64: String, callback: (Boolean, String, User?) -> Unit) {
        val body = mapOf("avatar" to avatarBase64)
        val call = apiService.updateAvatar(userId, body)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful && response.body() != null) {
                    callback(true, "Avatar actualizado correctamente", response.body())
                } else {
                    callback(false, "Error al actualizar el avatar", null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(false, "Error de red", null)
            }
        })
    }
}


