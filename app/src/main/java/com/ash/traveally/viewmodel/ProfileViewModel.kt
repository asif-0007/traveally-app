package com.ash.traveally.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.traveally.dao.FirebaseDao
import com.ash.traveally.models.User
import com.ash.traveally.repository.UserRepository
import com.ash.traveally.utils.NetworkResult
import com.ash.traveally.viewmodel.state.ProfileState
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository,
    private val firebaseDao: FirebaseDao
): ViewModel() {

    var profileState: ProfileState by mutableStateOf(ProfileState())

    init { getUser() }

    fun clearError() {
        profileState = profileState.copy(error = null)
    }

    private fun getUser() {
        viewModelScope.launch {
            profileState = profileState.copy(isLoading = true)
            val response = repository.getUser()
            profileState = when (response) {
                is NetworkResult.Error -> profileState.copy(error = true, isLoading = false)
                is NetworkResult.Success -> {
                    val user = response.data!!
                    profileState.copy(
                        id = user.id,
                        name = user.name,
                        username = user.username,
                        email = user.email,
                        phoneNumber = user.phoneNumber.toString(),
                        city = user.city,
                        country = user.country,
                        bio = user.bio,
                        photoUrl = user.photoUrl,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun uploadImage() {
        viewModelScope.launch {
            profileState = profileState.copy(isLoading = true)
            when (val response = firebaseDao.uploadImage(profileState.imageUri!!)) {
                is NetworkResult.Error -> profileState = profileState.copy(error = true, isLoading = false)
                is NetworkResult.Success -> {
                    val photoUrl = response.data!!
                    val user = User(
                        id = profileState.id,
                        name = profileState.name,
                        username = profileState.username,
                        email = profileState.email,
                        phoneNumber = profileState.phoneNumber.toLong(),
                        city = profileState.city,
                        country = profileState.country,
                        bio = profileState.bio,
                        photoUrl = photoUrl
                    )
                    updateUser(user)
                }
            }
        }
    }

    private suspend fun updateUser(user: User) {
        viewModelScope.launch {
            profileState = when (val response = repository.updateUser(user)) {
                is NetworkResult.Error -> profileState.copy(error = true, isLoading = false)
                is NetworkResult.Success -> {
                    val user = response.data!!
                    profileState.copy(
                        id = user.id,
                        name = user.name,
                        username = user.username,
                        email = user.email,
                        phoneNumber = user.phoneNumber.toString(),
                        city = user.city,
                        country = user.country,
                        bio = user.bio,
                        photoUrl = user.photoUrl,
                        isLoading = false
                    )
                }
            }
        }
    }
}