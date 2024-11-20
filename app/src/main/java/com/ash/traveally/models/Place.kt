package com.ash.traveally.models

data class Place(
    var id: Long,
    var name: String,
    var city: String,
    var country: String,
    var description: String,
    var price: Int,
    var rating: Float,
    var isFavourite: Boolean,
    var placePhoto: String,
    var hotelPhoto: String,
    var hasWifi: Boolean,
    var hasFood: Boolean,
    var hasTV: Boolean,
    var hasPool: Boolean,
    var hasSpa: Boolean,
    var hasLaundry: Boolean,
    var hostId: Long,
)