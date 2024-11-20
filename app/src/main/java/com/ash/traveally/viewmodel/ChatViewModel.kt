package com.ash.traveally.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.traveally.dao.FirebaseDao
import com.ash.traveally.repository.UserRepository
import com.ash.traveally.utils.NetworkResult
import com.ash.traveally.viewmodel.state.ChatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val firebaseDao: FirebaseDao,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val userId2: Long = savedStateHandle["userId"]!!

    var chatState: ChatState by mutableStateOf(ChatState(userId2 = userId2))

    init {
        getAllMessage()
    }

    // userId1 is current user and userId2 is the person with userId1 is chatting
    private fun getAllMessage() {
        viewModelScope.launch {
            chatState = chatState.copy(isLoading = true)
            val response1 = userRepository.getUser()
            chatState = when (response1) {
                is NetworkResult.Error -> chatState.copy(error = true, isLoading = false)
                is NetworkResult.Success -> {
                    val user1 = response1.data!!
                    chatState.copy(userId1 = user1.id)
                }
            }
            val response2 = userRepository.getUser(chatState.userId2)
            chatState = when (response2) {
                is NetworkResult.Error -> chatState.copy(error = true, isLoading = false)
                is NetworkResult.Success -> {
                    val user2 = response2.data!!
                    chatState.copy(
                        name = user2.name,
                        photoUrl = user2.photoUrl,
                        isLoading = false
                    )
                }
            }
            firebaseDao.getAllMessages(chatState.userId1, chatState.userId2).collectLatest {
                chatState = chatState.copy(messages = it)
            }
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            if (chatState.message.isNotBlank()) {
                firebaseDao.addMessage(message = chatState.message, userId1 = chatState.userId1, userId2 = chatState.userId2)
                chatState = chatState.copy(message = "")
            }
        }
    }

    fun setMessage(message: String) {
        chatState = chatState.copy(message = message)
    }

    fun clearError() {
        chatState = chatState.copy(error = null)
    }
}