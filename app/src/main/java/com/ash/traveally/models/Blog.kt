package com.ash.traveally.models

data class Blog(
    var id: Long? = 0L,
    var author: User? = null,
    var city: String,
    var country: String,
    var title: String,
    var introduction: String,
    var description: String,
    var isFavourite: Boolean = false,
    var isSaved: Boolean = false,
    var likes: Int = 0,
    var placePhoto: String = "",
    val time: Long = System.currentTimeMillis()
)