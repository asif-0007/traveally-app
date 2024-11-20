package com.ash.traveally.models

data class Message(
    val id: String = "",
    val message: String = "",
    val time: Long = System.currentTimeMillis(),
    val senderId: Long = 0L,
    val receiverId: Long = 0L
)