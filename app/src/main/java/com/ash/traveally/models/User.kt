package com.ash.traveally.models

data class User(
    val id: Long = 0L,
    val name: String,
    val username: String,
    val phoneNumber: Long,
    val email: String,
    val password: String? = null,
    val city: String,
    val country: String,
    var photoUrl: String = "https://i.pinimg.com/originals/1c/c5/35/1cc535901e32f18db87fa5e340a18aff.jpg",
    val bio: String
)
