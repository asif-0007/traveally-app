package com.ash.traveally.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ash.traveally.models.User
import com.ash.traveally.ui.components.dialog.FailureDialog
import com.ash.traveally.ui.components.dialog.LoaderDialog
import com.ash.traveally.ui.components.items.ChatItem
import com.ash.traveally.ui.components.text.SearchBar
import com.ash.traveally.viewmodel.ChatsViewModel
import com.ash.traveally.viewmodel.state.ChatsState

@Composable
fun ChatsScreen(
    viewModel: ChatsViewModel = hiltViewModel(),
    onDialogDismiss: () -> Unit = viewModel::clearError,
    onSearch: (String) -> Unit = viewModel::search,
    onItemClick: (User) -> Unit
) {
    val state: ChatsState = viewModel.chatsState

    val focusManager: FocusManager = LocalFocusManager.current

    if (state.isLoading) {
        LoaderDialog()
    }

    if (state.error != null) {
        FailureDialog("Something went wrong", onDialogDismiss = onDialogDismiss)
    }
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxHeight()
    ) {
        item {
            SearchBar(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                searchText = state.search,
                onSearchTextChanged = onSearch,
                onClearClick = {
                    viewModel.clearSearch()
                    focusManager.clearFocus()
                }
            )
        }
        items(state.users) {
            ChatItem(it, onItemClick = {
                viewModel.addUser(it.id)
                onItemClick(it)
            })
        }
    }
}