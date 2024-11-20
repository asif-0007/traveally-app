package com.ash.traveally.api

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatAPI {

    @GET("/api/chats")
    suspend fun getAllChats(): Response<List<Long>>

    @GET("/api/chats/search")
    suspend fun searchUser(@Query("query") query: String): Response<List<Long>>

    @POST("/api/chats/{userId}")
    suspend fun addChat(@Path("userId") userId: Long): Response<String>

    @DELETE("/api/chats/{userId}")
    suspend fun deleteChat(@Path("userId") userId: Long): Response<String>
}