package com.ash.traveally.repository

import com.ash.traveally.api.PlaceAPI
import com.ash.traveally.models.Place
import com.ash.traveally.utils.NetworkResult
import javax.inject.Inject

class PlaceRepository @Inject constructor(
    private val placeAPI: PlaceAPI
) {
    suspend fun getAllPlaces(): NetworkResult<List<Place>> {
        try {
            val response = placeAPI.getAllPlaces()
            if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body()!!);
            }
        } catch (e : Exception) {
            return NetworkResult.Error(e.message);
        }
        return NetworkResult.Error("Something went wrong")
    }

    suspend fun getPlace(id: Long): NetworkResult<Place> {
        try {
            val response = placeAPI.getPlace(id)
            if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body()!!);
            }
        } catch (e : Exception) {
            return NetworkResult.Error(e.message);
        }
        return NetworkResult.Error("Something went wrong")
    }

    suspend fun updatePlace(place: Place): NetworkResult<Place> {
        try {
            val response = placeAPI.updatePlace(place)
            if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body()!!);
            }
        } catch (e : Exception) {
            return NetworkResult.Error(e.message);
        }
        return NetworkResult.Error("Something went wrong")
    }
}