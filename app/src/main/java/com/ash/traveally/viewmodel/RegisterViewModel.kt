package com.ash.traveally.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.traveally.models.User
import com.ash.traveally.repository.UserRepository
import com.ash.traveally.utils.AuthValidator
import com.ash.traveally.utils.NetworkResult
import com.ash.traveally.viewmodel.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    var registerState: RegisterState by mutableStateOf(RegisterState())

    fun setName(name: String) {
        registerState = registerState.copy(name = name)
    }

    fun setEmail(email: String) {
        registerState = registerState.copy(email = email)
    }

    fun setUsername(username: String) {
        registerState = registerState.copy(username = username)
    }

    fun setPhoneNumber(phoneNumber: String) {
        registerState = registerState.copy(phoneNumber = phoneNumber)
    }

    fun setCity(city: String) {
        registerState = registerState.copy(city = city)
    }

    fun setCountry(country: String) {
        registerState = registerState.copy(country = country)
    }

    fun setBio(bio: String) {
        registerState = registerState.copy(bio = bio)
    }

    fun setPassword(password: String) {
        registerState = registerState.copy(password = password)
    }

    fun setConfirmPassword(confirmPassword: String) {
        registerState = registerState.copy(confirmPassword = confirmPassword)
    }

    fun clearError() {
        registerState = registerState
            .copy(error = null)
    }

    fun registerUser() {
        if (!validateCredentials()) return
        viewModelScope.launch {
            registerState = registerState.copy(isLoading = true)
            val registerRequest = User(
                name = registerState.name,
                email = registerState.email,
                username = registerState.username,
                phoneNumber = registerState.phoneNumber.toLong(),
                city = registerState.city,
                country = registerState.country,
                bio = registerState.bio,
                password = registerState.password,
            )
            val response = repository.registerUser(registerRequest)
            registerState = when (response) {
                is NetworkResult.Error -> registerState.copy(error = response.message, isLoading = false, isRegisteredIn = false)
                is NetworkResult.Success -> {
                    registerState.copy(response = response.data, isLoading = false, isRegisteredIn = true)
                }
            }
        }
    }

    private fun validateCredentials(): Boolean {
        val isValidName = AuthValidator.isValidName(registerState.name)
        val isValidEmail = AuthValidator.isValidEmail(registerState.email)
        val isValidPassword = AuthValidator.isValidPassword(registerState.password)
        val isValidUsername = AuthValidator.isValidUsername(registerState.username)
        val isValidPhoneNumber = AuthValidator.isValidPhoneNumber(registerState.phoneNumber)
        val arePasswordAndConfirmPasswordSame = AuthValidator.arePasswordAndConfirmPasswordSame(
            registerState.password,
            registerState.confirmPassword
        )
        registerState = registerState.copy(
            isValidName = isValidName,
            isValidEmail = isValidEmail,
            isValidPassword = isValidPassword,
            isValidUsername = isValidUsername,
            isValidPhoneNumber = isValidPhoneNumber,
            isValidConfirmPassword = arePasswordAndConfirmPasswordSame
        )

        return isValidEmail && isValidPassword && isValidUsername && isValidPhoneNumber && arePasswordAndConfirmPasswordSame
    }
}