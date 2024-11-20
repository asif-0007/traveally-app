package com.ash.traveally.repository

import com.ash.traveally.api.BlogAPI
import com.ash.traveally.models.Blog
import com.ash.traveally.utils.NetworkResult
import javax.inject.Inject

class BlogRepository @Inject constructor(
    private val blogAPI: BlogAPI
) {
    suspend fun getAllBlogs(): NetworkResult<List<Blog>> {
        try {
            val response = blogAPI.getAllBlogs()
            if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body()!!)
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Error("Something went wrong")
    }

    suspend fun getBlog(id: Long): NetworkResult<Blog> {
        try {
            val response = blogAPI.getBlog(id)
            if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body()!!)
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Error("Something went wrong")
    }

    suspend fun addBlog(blog: Blog): NetworkResult<Blog> {
        try {
            val response = blogAPI.addBlog(blog)
            if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body()!!)
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Error("Something went wrong")
    }

    suspend fun updateBlog(blog: Blog): NetworkResult<Blog> {
        try {
            val response = blogAPI.updateBlog(blog)
            if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body()!!)
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Error("Something went wrong")
    }
}
