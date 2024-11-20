package com.ash.traveally.api

import com.ash.traveally.models.Blog
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BlogAPI {

    @GET("/api/blogs")
    suspend fun getAllBlogs(): Response<List<Blog>>

    @GET("/api/blog/{id}")
    suspend fun getBlog(@Path("id") id: Long): Response<Blog>

    @POST("/api/blog/create")
    suspend fun addBlog(@Body blog: Blog): Response<Blog>

    @PUT("/api/blog/update")
    suspend fun updateBlog(@Body blog: Blog): Response<Blog>
}
