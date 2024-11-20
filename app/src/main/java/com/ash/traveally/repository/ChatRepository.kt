package com.ash.traveally.repository

import com.ash.traveally.api.ChatAPI
import com.ash.traveally.utils.NetworkResult
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val chatAPI: ChatAPI
) {
    suspend fun getAllChats(): NetworkResult<List<Long>> {
        try {
            val response = chatAPI.getAllChats()
            if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body()!!)
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Error("Something went wrong")
    }

    suspend fun searchUser(query: String): NetworkResult<List<Long>> {
        try {
            val response = chatAPI.searchUser(query)
            if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body()!!)
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Error("Something went wrong")
    }

    suspend fun addChat(userId: Long): NetworkResult<String> {
        try {
            val response = chatAPI.addChat(userId)
            if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body()!!)
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Error("Something went wrong")
    }

    suspend fun deleteChat(userId: Long): NetworkResult<String> {
        try {
            val response = chatAPI.deleteChat(userId)
            if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body()!!)
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Error("Something went wrong")
    }
}