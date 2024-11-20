package com.ash.traveally.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.traveally.repository.PlaceRepository
import com.ash.traveally.repository.UserRepository
import com.ash.traveally.utils.NetworkResult
import com.ash.traveally.viewmodel.state.PlaceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val placeId: Long = savedStateHandle["placeId"]!!

    var placeState: PlaceState by mutableStateOf(PlaceState(id = placeId))

    init { getPlace() }


    fun getPlace() {
        viewModelScope.launch {
            placeState = placeState.copy(isLoading = true)
            val response = placeRepository.getPlace(placeState.id)
            when (response) {
                is NetworkResult.Error -> placeState = placeState.copy(error = response.message, isLoading = false)
                is NetworkResult.Success -> {
                    val place = response.data!!
                    placeState = placeState.copy(
                        name = place.name,
                        city = place.city,
                        country = place.country,
                        description = place.description,
                        rating = place.rating,
                        price = place.price,
                        placePhoto = place.placePhoto,
                        hotelPhoto = place.hotelPhoto,
                        isFavourite = place.isFavourite,
                        hasWifi = place.hasWifi,
                        hasFood = place.hasFood,
                        hasTV = place.hasTV,
                        hasPool = place.hasPool,
                        hasSpa = place.hasSpa,
                        hasLaundry = place.hasLaundry,
                        hostId = place.hostId
                    )
                    getHost()
                }
            }
        }
    }

    suspend fun getHost() {
        val response = userRepository.getUser(placeState.hostId)
        placeState = when (response) {
            is NetworkResult.Error -> placeState.copy(error = response.message, isLoading = false)
            is NetworkResult.Success -> {
                val host = response.data!!
                placeState.copy(
                    hostName = host.name,
                    hostPhoto = host.photoUrl,
                    hostBio = host.bio,
                    isLoading = false
                )
            }
        }
    }

    fun clearError() {
        placeState = placeState.copy(error = null, isLoading = false)
    }
}