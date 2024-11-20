package com.ash.traveally.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ash.traveally.R
import com.ash.traveally.models.Blog
import com.ash.traveally.ui.components.dialog.FailureDialog
import com.ash.traveally.ui.components.dialog.LoaderDialog
import com.ash.traveally.ui.components.items.BlogItem
import com.ash.traveally.ui.components.text.SearchBar
import com.ash.traveally.ui.theme.LightGreen
import com.ash.traveally.viewmodel.BlogsViewModel
import com.ash.traveally.viewmodel.state.BlogsState

@Composable
fun BlogsScreen(
    viewModel: BlogsViewModel = hiltViewModel(),
    onDialogDismiss: () -> Unit = viewModel::clearError,
    onLikeClick: (Blog) -> Unit = viewModel::likeBlog,
    onSearch: (String) -> Unit = viewModel::search,
    onSavedClick: () -> Unit = viewModel::savedBlogs,
    onItemClick: (Blog) -> Unit,
    onAddBlogClick: () -> Unit
) {
    val state: BlogsState = viewModel.blogsState

    val focusManager: FocusManager = LocalFocusManager.current

    if (state.isLoading) {
        LoaderDialog()
    }

    if (state.error != null) {
        FailureDialog("Something went wrong", onDialogDismiss = onDialogDismiss)
    }

    Column {
        SearchBar(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            searchText = state.search,
            onSearchTextChanged = onSearch,
            onClearClick = {
                viewModel.clearSearch()
                focusManager.clearFocus()
            }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.blogs) { blog ->
                BlogItem(blog = blog, onItemClick = onItemClick, onLikeClick = onLikeClick)
            }
        }
    }
    Column (
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        FloatingActionButton(
            onClick = onSavedClick,
            containerColor = LightGreen
        ) {
            Icon(painterResource(id = R.drawable.ic_bookmark),"")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        FloatingActionButton(
            onClick = onAddBlogClick,
            containerColor = LightGreen
        ) {
            Icon(Icons.Filled.Add,"")
        }
    }
}