package com.ash.traveally.viewmodel.state

import androidx.compose.runtime.mutableStateListOf
import com.ash.traveally.models.Message

data class ChatState(
    val messages: List<Message> = mutableStateListOf(),
    val userId1: Long = 0L,
    val userId2: Long = 0L,
    val name: String = "",
    val photoUrl: String = "",
    val message: String = "",
    val isLoading: Boolean = false,
    val error: Boolean? = null
)