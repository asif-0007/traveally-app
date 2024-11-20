package com.ash.traveally.viewmodel.state

import androidx.compose.runtime.mutableStateListOf
import com.ash.traveally.models.Blog

data class BlogsState(
    val blogs: List<Blog> = mutableStateListOf(),
    val backup: List<Blog> = mutableStateListOf(),
    val search: String = "",
    val isLoading: Boolean = false,
    val error: Boolean? = null
)