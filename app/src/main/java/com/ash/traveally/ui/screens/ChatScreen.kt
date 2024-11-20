package com.ash.traveally.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ash.traveally.ui.components.buttons.BackButton
import com.ash.traveally.ui.components.buttons.CallButton
import com.ash.traveally.ui.components.dialog.FailureDialog
import com.ash.traveally.ui.components.dialog.LoaderDialog
import com.ash.traveally.ui.components.text.MessageTextField
import com.ash.traveally.ui.theme.LightGreen
import com.ash.traveally.ui.theme.MontserratAlternates
import com.ash.traveally.viewmodel.ChatViewModel
import com.ash.traveally.viewmodel.state.ChatState

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onSendClick: () -> Unit = viewModel::sendMessage,
    onDialogDismiss: () -> Unit = viewModel::clearError,
    onMessageChange: (String) -> Unit = viewModel::setMessage,
    onBackClick: () -> Unit
) {
    val state: ChatState = viewModel.chatState

    if (state.isLoading) {
        LoaderDialog()
    }

    if (state.error != null) {
        FailureDialog("Something went wrong", onDialogDismiss = onDialogDismiss)
    }

    if (!state.isLoading && state.error == null) {
        Column {
            Row(
                modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .background(color = LightGreen, shape = RoundedCornerShape(6.dp))
                .shadow(2.dp)
                .padding(vertical = 8.dp),
            ) {
                BackButton(modifier = Modifier.padding(8.dp), onClick = onBackClick)
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(state.photoUrl)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Black, CircleShape)
                )
                Text(
                    text = state.name,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = MontserratAlternates,
                        fontWeight = FontWeight.W600
                    ),
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                CallButton(modifier = Modifier.padding(8.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    reverseLayout = true
                ) {
                    items(state.messages) { message ->
                        if (message.senderId == state.userId2) {
                            // left side
                            Text(
                                text = message.message,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = MontserratAlternates,
                                    fontWeight = FontWeight.W500
                                ),
                                modifier = Modifier
                                    .background(
                                        color = LightGreen,
                                        shape = RoundedCornerShape(
                                            topStart = 8.dp,
                                            bottomEnd = 8.dp
                                        )
                                    )
                                    .padding(12.dp)
                                    .fillMaxWidth(0.5f)
                            )
                        } else {
                            Row {
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = message.message,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = MontserratAlternates,
                                        fontWeight = FontWeight.W500
                                    ),
                                    modifier = Modifier
                                        .background(
                                            color = LightGreen,
                                            shape = RoundedCornerShape(
                                                topEnd = 8.dp,
                                                bottomStart = 8.dp
                                            )
                                        )
                                        .padding(12.dp)
                                        .fillMaxWidth(0.5f)
                                )
                            }
                        }
                    }
                }
                MessageTextField(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(
                            bottom = 12.dp,
                            start = 12.dp,
                            end = 8.dp
                        ),
                    value = state.message,
                    onSend = onSendClick,
                    onMessageChange = onMessageChange
                )
            }
        }
    }
}