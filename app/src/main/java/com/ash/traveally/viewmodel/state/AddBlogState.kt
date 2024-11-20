package com.ash.traveally.viewmodel.state

import android.net.Uri

data class AddBlogState(
    val title: String = "",
    val introduction: String = "",
    val description: String = "",
    val city: String = "",
    val country: String = "",
    val placePhoto: String = "",
    val isLoading: Boolean = false,
    val error: Boolean? = null,
    var imageUri: Uri? = null
)