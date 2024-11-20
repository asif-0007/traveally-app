package com.ash.traveally.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ash.traveally.ui.components.buttons.CustomButton
import com.ash.traveally.ui.components.dialog.FailureDialog
import com.ash.traveally.ui.components.dialog.LoaderDialog
import com.ash.traveally.ui.theme.MontserratAlternates
import com.ash.traveally.viewmodel.ProfileViewModel
import com.ash.traveally.viewmodel.state.ProfileState

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onDialogDismiss: () -> Unit = viewModel::clearError,
    uploadImage: () -> Unit = viewModel::uploadImage
) {
    val state: ProfileState = viewModel.profileState

    if (state.isLoading) {
        LoaderDialog()
    }

    if (state.error != null) {
        FailureDialog("Something went wrong", onDialogDismiss = onDialogDismiss)
    }

    if (!state.isLoading && state.error == null) {
        ProfileContent(
            name = state.name,
            username = state.username,
            email = state.email,
            phoneNumber = state.phoneNumber,
            city = state.city,
            country = state.country,
            bio = state.bio,
            photoUrl = state.photoUrl,
            state = state,
            uploadImage = uploadImage
        )
    }
}

@Composable
fun ProfileContent(
    name: String,
    username: String,
    email: String,
    phoneNumber: String,
    city: String,
    country: String,
    bio: String,
    photoUrl: String,
    state: ProfileState,
    uploadImage: () -> Unit
) {
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            state.imageUri = it
            uploadImage()
        }
    )

    Column (
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(photoUrl)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(4.dp, Color.Black, CircleShape)
        )
        CustomButton(text = "Change Picture", onClick = {
            singlePhotoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        })
        Spacer(modifier = Modifier.padding(6.dp))
        Text(
            text = "Name : $name",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratAlternates
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider()
        Text(
            text = "Username : @$username",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratAlternates
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider()
        Text(
            text = "Phone Number : $phoneNumber",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratAlternates
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider()
        Text(
            text = "Email : $email",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratAlternates
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider()
        Text(
            text = "Location : $city, $country",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratAlternates
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider()
        Text(
            text = "Bio : $bio",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = MontserratAlternates
            ),
            modifier = Modifier.padding(horizontal = 8.dp).align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Contact us : traveally-care@gmail.com" ,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = MontserratAlternates
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}