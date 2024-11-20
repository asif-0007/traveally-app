package com.ash.traveally.viewmodel.state

import com.ash.traveally.models.RegisterResponse


data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val country: String = "",
    val bio: String = "",
    val confirmPassword: String = "",
    val response: RegisterResponse? = null,
    val isLoading: Boolean = false,
    val isValidName: Boolean? = null,
    val isValidEmail: Boolean? = null,
    val isValidUsername: Boolean? = null,
    val isValidPhoneNumber: Boolean? = null,
    val isValidPassword: Boolean? = null,
    val isValidConfirmPassword: Boolean? = null,
    val error: String? = null,
    val isRegisteredIn: Boolean = false,
)