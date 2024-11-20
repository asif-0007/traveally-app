package com.ash.traveally.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.traveally.models.Blog
import com.ash.traveally.repository.BlogRepository
import com.ash.traveally.utils.NetworkResult
import com.ash.traveally.viewmodel.state.BlogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    private val blogRepository: BlogRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val blogId: Long = savedStateHandle["blogId"]!!

    var blogState: BlogState by mutableStateOf(BlogState(id = blogId))

    init { getBlog() }

    private fun getBlog() {
        viewModelScope.launch {
            blogState = blogState.copy(isLoading = true)
            when (val response = blogRepository.getBlog(blogState.id)) {
                is NetworkResult.Error -> blogState = blogState.copy(error = true, isLoading = false)
                is NetworkResult.Success -> {
                    val blog = response.data!!
                    blogState = blogState.copy(
                        placePhoto = blog.placePhoto,
                        city = blog.city,
                        country = blog.country,
                        title = blog.title,
                        introduction = blog.introduction,
                        description = blog.description,
                        time = blog.time,
                        authorName = blog.author!!.name,
                        authorPhotoUrl = blog.author!!.photoUrl,
                        isLoading = false,
                        blog = blog
                    )
                }
            }
        }
    }

    fun saveBlog(blog: Blog?) {
        viewModelScope.launch {
            if (blog == null) return@launch
            blogState = blogState.copy(isLoading = true)
            blog.isSaved = !blog.isSaved
            when (val response = blogRepository.updateBlog(blog)) {
                is NetworkResult.Error -> blogState = blogState.copy(error = true, isLoading = false, isSaved = !blogState.isSaved)
                is NetworkResult.Success -> {
                    val data = response.data!!
                    blogState = blogState.copy(isSaved = data.isSaved, isLoading = false)
                }
            }
        }
    }

    fun clearError() {
        blogState = blogState.copy(error = null, isLoading = false)
    }
}