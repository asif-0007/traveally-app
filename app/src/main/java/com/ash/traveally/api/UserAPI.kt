package com.ash.traveally.api

import com.ash.traveally.models.LoginRequest
import com.ash.traveally.models.LoginResponse
import com.ash.traveally.models.RegisterResponse
import com.ash.traveally.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPI {

    @POST("/api/auth/register")
    suspend fun register(@Body registerRequest: User) : Response<RegisterResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @GET("/api/auth/user")
    suspend fun getUser(@Header("Authorization") token: String) : Response<User>

    @GET("/api/auth/user/{id}")
    suspend fun getUser(@Path("id") id: Long, @Header("Authorization") token: String) : Response<User>

    @PUT("/api/auth/user/update")
    suspend fun updateUser(@Body user: User, @Header("Authorization") token: String) : Response<User>
}