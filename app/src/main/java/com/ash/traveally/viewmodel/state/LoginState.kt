package com.ash.traveally.viewmodel.state

import com.ash.traveally.models.LoginResponse

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isValidEmail: Boolean? = null,
    val isValidPassword: Boolean? = null,
    val isLoading: Boolean = false,
    val loginResponse: LoginResponse? = null,
    val error: String? = null,
    val isLoggedIn: Boolean = false
)