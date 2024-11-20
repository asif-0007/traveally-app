package com.ash.traveally.viewmodel.state

import android.net.Uri
import com.ash.traveally.models.User

data class ProfileState(
    val id: Long = 0L,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val country: String = "",
    val bio: String = "",
    val photoUrl: String = "",
    val isLoading: Boolean = false,
    val error: Boolean? = null,
    var imageUri: Uri? = null
)