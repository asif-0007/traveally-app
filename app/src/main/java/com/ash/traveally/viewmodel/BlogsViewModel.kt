package com.ash.traveally.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.traveally.models.Blog
import com.ash.traveally.repository.BlogRepository
import com.ash.traveally.utils.NetworkResult
import com.ash.traveally.viewmodel.state.BlogsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogsViewModel @Inject constructor(
    private val repository: BlogRepository
) : ViewModel() {

    var blogsState: BlogsState by mutableStateOf(BlogsState())

    private var job: Job? = null

    init { getAllBlogs() }

    fun clearError() {
        blogsState = blogsState.copy(error = null)
    }

    private fun getAllBlogs() {
        viewModelScope.launch {
            blogsState = blogsState.copy(isLoading = true)
            val response = repository.getAllBlogs()
            blogsState = when (response) {
                is NetworkResult.Error -> blogsState.copy(error = true, isLoading = false)
                is NetworkResult.Success -> {
                    val blogs = response.data!!
                    blogsState.copy(blogs = blogs, backup = blogs, isLoading = false)
                }
            }
        }
    }

    fun likeBlog(blog: Blog) {
        viewModelScope.launch {
            blogsState = blogsState.copy(isLoading = true)
            blog.isFavourite = !blog.isFavourite
            when (val response = repository.updateBlog(blog)) {
                is NetworkResult.Error -> {
                    blogsState = blogsState.copy(error = true, isLoading = false)
                    blog.isFavourite = !blog.isFavourite
                }
                is NetworkResult.Success -> {
                    val data = response.data!!
                    blogsState = blogsState.copy(isLoading = false)
                    blog.likes = data.likes
                }
            }
        }
    }

    fun search(query: String) {
        blogsState = blogsState.copy(search = query)
        job?.cancel()
        job = viewModelScope.launch {
            delay(1000)
            blogsState = blogsState.copy(isLoading = true)
            val blogs = blogsState.backup
            val result = mutableListOf<Blog>()
            blogs.forEach {
                if (
                    it.title.contains(query) ||
                    it.introduction.contains(query) ||
                    it.description.contains(query) ||
                    it.city.contains(query) ||
                    it.country.contains(query)
                ) {
                    result.add(it)
                }
            }
            blogsState = blogsState.copy(blogs = result, isLoading = false)
        }
    }


    fun clearSearch() {
        blogsState = blogsState.copy(blogs = blogsState.backup, search = "")
    }

    fun savedBlogs() {
        viewModelScope.launch {
            blogsState = blogsState.copy(isLoading = true)
            val blogs = blogsState.backup
            val result = mutableListOf<Blog>()
            blogs.forEach {
                if (it.isSaved) {
                    result.add(it)
                }
            }
            blogsState = blogsState.copy(blogs = result, isLoading = false)
        }
    }
}
