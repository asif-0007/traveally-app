package com.ash.traveally.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.traveally.models.User
import com.ash.traveally.repository.ChatRepository
import com.ash.traveally.repository.UserRepository
import com.ash.traveally.utils.NetworkResult
import com.ash.traveally.viewmodel.state.ChatsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
): ViewModel() {

    var chatsState: ChatsState by mutableStateOf(ChatsState())

    private var job: Job? = null

    init {
        getAllChats()
    }

    fun clearError() {
        chatsState = chatsState.copy(error = null)
    }

    private fun getAllChats() {
        viewModelScope.launch {
            chatsState = chatsState.copy(isLoading = true)
            when (val response = chatRepository.getAllChats()) {
                is NetworkResult.Error -> chatsState = chatsState.copy(error = true, isLoading = false)
                is NetworkResult.Success -> {
                    val ids = response.data!!
                    val users = mutableListOf<User>()
                    for (id in ids) {
                        val user = getUser(id)
                        if (user != null) {
                            users.add(user)
                        }
                    }
                    chatsState = chatsState.copy(users = users, backup = users, isLoading = false)
                }
            }
        }
    }

    private suspend fun getUser(id: Long): User? {
        when (val response = userRepository.getUser(id)) {
            is NetworkResult.Error -> chatsState = chatsState.copy(error = true, isLoading = false)
            is NetworkResult.Success -> {
                return response.data!!
            }
        }
        return null
    }

    fun search(query: String) {
        chatsState = chatsState.copy(search = query)
        job?.cancel()
        job = viewModelScope.launch {
            delay(1000)
            chatsState = chatsState.copy(isLoading = true)
            when (val response = chatRepository.searchUser(query)) {
                is NetworkResult.Error -> chatsState = chatsState.copy(error = true, isLoading = false)
                is NetworkResult.Success -> {
                    val ids = response.data!!
                    val users = mutableListOf<User>()
                    for (id in ids) {
                        val user = getUser(id)
                        if (user != null) {
                            users.add(user)
                        }
                    }
                    chatsState = chatsState.copy(users = users, isLoading = false)
                }
            }
        }
    }

    fun clearSearch() {
        chatsState = chatsState.copy(users = chatsState.backup, search = "")
    }

    fun addUser(userId: Long) {
        viewModelScope.launch {
            chatsState = chatsState.copy(isLoading = true)
            chatsState = when (chatRepository.addChat(userId = userId)) {
                is NetworkResult.Error -> chatsState.copy(error = true, isLoading = false)
                is NetworkResult.Success -> {
                    chatsState.copy(isLoading = false)
                }
            }
        }
    }
}