package com.ash.traveally.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.traveally.models.Place
import com.ash.traveally.repository.PlaceRepository
import com.ash.traveally.utils.NetworkResult
import com.ash.traveally.viewmodel.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PlaceRepository
): ViewModel() {

    var homeState: HomeState by mutableStateOf(HomeState())

    private var job: Job? = null

    init { getAllPlaces() }

    fun clearError() {
        homeState = homeState.copy(error = null)
    }

    private fun getAllPlaces() {
        viewModelScope.launch {
            homeState = homeState.copy(isLoading = true)
            val response = repository.getAllPlaces()
            homeState = when (response) {
                is NetworkResult.Error -> homeState.copy(error = true, isLoading = false)
                is NetworkResult.Success -> {
                    val places = response.data!!
                    homeState.copy(places = places, backup = places, isLoading = false)
                }
            }
        }
    }

    fun likePlace(place: Place) {
        viewModelScope.launch {
            homeState = homeState.copy(isLoading = true)
            place.isFavourite = !place.isFavourite
            val response = repository.updatePlace(place)
            when (response) {
                is NetworkResult.Error -> {
                    homeState = homeState.copy(error = true, isLoading = false)
                    place.isFavourite = !place.isFavourite
                }
                is NetworkResult.Success -> {
                    homeState = homeState.copy(isLoading = false)
                }
            }
        }
    }

    fun search(query: String) {
        homeState = homeState.copy(search = query)
        job?.cancel()
        job = viewModelScope.launch {
            delay(1000)
            homeState = homeState.copy(isLoading = true)
            val places = homeState.backup
            val result = mutableListOf<Place>()
            places.forEach {
                if (
                    it.name.contains(query) ||
                    it.city.contains(query) ||
                    it.country.contains(query) ||
                    it.description.contains(query)
                ) {
                    result.add(it)
                }
            }
            homeState = homeState.copy(places = result, isLoading = false)
        }
    }


    fun clearSearch() {
        homeState = homeState.copy(places = homeState.backup, search = "")
    }
}