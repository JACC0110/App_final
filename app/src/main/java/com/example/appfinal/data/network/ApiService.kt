package com.example.appfinal.data.network

import com.example.appfinal.data.model.Destino
import com.example.appfinal.data.model.LoginRequest
import com.example.appfinal.data.model.LoginResponse
import com.example.appfinal.data.model.RegisterRequest
import com.example.appfinal.data.model.RegisterResponse
import com.example.appfinal.data.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("users/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("users")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>

    @PUT("users/{id}/password")
    fun changePassword(
        @Path("id") userId: Int,
        @Body body: Map<String, String>
    ): Call<LoginResponse>

    @PUT("users/{id}/avatar")
    fun updateAvatar(
        @Path("id") userId: Int,
        @Body body: Map<String, String>
    ): Call<User>

    @GET("destinos")
    suspend fun getDestinos(): Response<List<Destino>>

    @GET("destinos/{id}")
    suspend fun getDestinoById(@Path("id") id: Int): Response<Destino>
}
