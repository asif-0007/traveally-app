package com.ash.traveally.models

data class LoginResponse(
    val accessToken: String,
    val userId: Int,
    val tokenType: String
)