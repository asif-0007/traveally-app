package com.ash.traveally.viewmodel.state

import androidx.compose.runtime.mutableStateListOf
import com.ash.traveally.models.User

data class ChatsState(
    val users: MutableList<User> = mutableStateListOf(),
    val backup: MutableList<User> = mutableStateListOf(),
    val search: String = "",
    val isLoading: Boolean = false,
    val error: Boolean? = null
)
