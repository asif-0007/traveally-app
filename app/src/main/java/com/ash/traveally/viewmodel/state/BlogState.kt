package com.ash.traveally.viewmodel.state

import com.ash.traveally.models.Blog

data class BlogState(
    val id: Long,
    val title: String = "",
    val introduction: String = "",
    val description: String = "",
    val city: String = "",
    val country: String = "",
    val placePhoto: String = "",
    val time: Long = 0L,
    val authorName: String = "",
    val authorPhotoUrl: String = "",
    val isLoading: Boolean = false,
    var isSaved: Boolean = false,
    val error: Boolean? = null,
    val blog: Blog? = null
)