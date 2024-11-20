package com.ash.traveally.viewmodel.state

data class PlaceState(
    val id: Long,
    val name: String =  "",
    val city: String = "",
    val country: String = "",
    val description: String = "",
    val price: Int = 0,
    val rating: Float = 0.0f,
    val isFavourite: Boolean = false,
    val placePhoto: String = "",
    val hotelPhoto: String = "",
    val hasWifi: Boolean = false,
    val hasFood: Boolean = false,
    val hasTV: Boolean = false,
    val hasPool: Boolean = false,
    val hasSpa: Boolean = false,
    val hasLaundry: Boolean = false,
    val hostId: Long = 0L,
    val hostName: String = "",
    val hostPhoto: String = "",
    val hostBio: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)