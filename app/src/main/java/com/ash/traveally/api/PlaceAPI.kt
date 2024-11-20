package com.ash.traveally.api;

import com.ash.traveally.models.Place
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlaceAPI {

    @GET("/api/places")
    suspend fun getAllPlaces() : Response<List<Place>>

    @GET("/api/place/{id}")
    suspend fun getPlace(@Path("id") id : Long) : Response<Place>

    @PUT("/api/place/update")
    suspend fun updatePlace(@Body place: Place) : Response<Place>
}
