package com.ash.traveally.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.traveally.models.LoginRequest
import com.ash.traveally.repository.UserRepository
import com.ash.traveally.utils.AuthValidator
import com.ash.traveally.utils.NetworkResult
import com.ash.traveally.utils.TokenManager
import com.ash.traveally.viewmodel.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
    private val tokenManager: TokenManager
): ViewModel() {

    var loginState: LoginState by mutableStateOf(LoginState())

    init {
        if (tokenManager.getToken() != null) {
            loginState = loginState.copy(isLoggedIn = true)
        }
    }

    fun setEmail(email: String) {
        loginState = loginState.copy(email = email)
    }

    fun setPassword(password: String) {
        loginState = loginState.copy(password = password)
    }

    fun clearError() {
        loginState = loginState.copy(error = null)
    }

    fun loginUser() {
        if (!validateCredentials()) return
        viewModelScope.launch {
            loginState = loginState.copy(isLoading = true)
            val loginRequest = LoginRequest(email = loginState.email, password = loginState.password)
            val response = repository.loginUser(loginRequest)
            loginState = when (response) {
                is NetworkResult.Error -> loginState.copy(error = response.message, isLoading = false, isLoggedIn = false)
                is NetworkResult.Success -> {
                    tokenManager.saveToken(response.data!!.accessToken)
                    loginState.copy(loginResponse = response.data, isLoading = false, isLoggedIn = true)
                }
            }
        }
    }

    private fun validateCredentials(): Boolean {
        val isValidEmail = AuthValidator.isValidEmail(loginState.email)
        val isValidPassword = AuthValidator.isValidPassword(loginState.password)

        loginState = loginState.copy(
            isValidEmail = isValidEmail,
            isValidPassword = isValidPassword
        )

        return isValidEmail && isValidPassword
    }
}