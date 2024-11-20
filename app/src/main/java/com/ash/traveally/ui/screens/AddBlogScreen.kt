package com.ash.traveally.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ash.traveally.R
import com.ash.traveally.ui.components.dialog.FailureDialog
import com.ash.traveally.ui.components.dialog.LoaderDialog
import com.ash.traveally.ui.components.etc.TopAppBar
import com.ash.traveally.ui.theme.LightGreen
import com.ash.traveally.ui.theme.MontserratAlternates
import com.ash.traveally.viewmodel.AddBlogViewModel
import com.ash.traveally.viewmodel.state.AddBlogState

@Composable
fun AddBlogScreen(
    viewModel: AddBlogViewModel = hiltViewModel(),
    onTitleChange: (String) -> Unit = viewModel::setTitle,
    onCityChange: (String) -> Unit = viewModel::setCity,
    onCountryChange: (String) -> Unit = viewModel::setCountry,
    onIntroductionChange: (String) -> Unit = viewModel::setIntroduction,
    onDescriptonChange: (String) -> Unit = viewModel::setDescription,
    onDialogDismiss: () -> Unit = viewModel::clearError,
    uploadImage: () -> Unit = viewModel::uploadImage,
    onAddBlogClick: () -> Unit
) {
    val state: AddBlogState = viewModel.addBlogState

    if (state.isLoading) {
        LoaderDialog()
    }

    if (state.error != null) {
        FailureDialog("Something went wrong", onDialogDismiss = onDialogDismiss)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            state.imageUri = it
            uploadImage()
        }
    )

    Column (
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(12.dp)
    ) {
        TopAppBar()
        OutlinedTextField(
            value = state.title,
            label = { Text(text = "Title", fontSize = 14.sp, fontFamily = MontserratAlternates, fontWeight = FontWeight.W500) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("Title"),
            onValueChange = onTitleChange,
            textStyle = TextStyle(fontSize = 15.sp, fontFamily = MontserratAlternates, color = Color.Black),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LightGreen, focusedLabelColor = LightGreen),
            singleLine = true
        )
        Row (horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = state.city,
                label = { Text(text = "City", fontSize = 14.sp, fontFamily = MontserratAlternates, fontWeight = FontWeight.W500) },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .testTag("City"),
                onValueChange = onCityChange,
                textStyle = TextStyle(fontSize = 15.sp, fontFamily = MontserratAlternates, color = Color.Black),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LightGreen, focusedLabelColor = LightGreen)
            )
            OutlinedTextField(
                value = state.country,
                label = { Text(text = "Country", fontSize = 14.sp, fontFamily = MontserratAlternates, fontWeight = FontWeight.W500) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("Country"),
                onValueChange = onCountryChange,
                textStyle = TextStyle(fontSize = 15.sp, fontFamily = MontserratAlternates, color = Color.Black),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LightGreen, focusedLabelColor = LightGreen)
            )
        }
        OutlinedTextField(
            value = state.introduction,
            label = { Text(text = "Introduction", fontSize = 14.sp, fontFamily = MontserratAlternates, fontWeight = FontWeight.W500) },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .testTag("Introduction"),
            onValueChange = onIntroductionChange,
            textStyle = TextStyle(fontSize = 15.sp, fontFamily = MontserratAlternates, color = Color.Black),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LightGreen, focusedLabelColor = LightGreen)
        )
        OutlinedTextField(
            value = state.description,
            label = { Text(text = "Description", fontSize = 14.sp, fontFamily = MontserratAlternates, fontWeight = FontWeight.W500) },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .testTag("Description"),
            onValueChange = onDescriptonChange,
            textStyle = TextStyle(fontSize = 15.sp, fontFamily = MontserratAlternates, color = Color.Black),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LightGreen, focusedLabelColor = LightGreen)
        )
    }
    Column (
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        FloatingActionButton(
            onClick = {
                singlePhotoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
            Icon(painterResource(id = R.drawable.ic_attach),"")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        FloatingActionButton(onClick = {
            viewModel.insertBlog()
            onAddBlogClick()
        }) {
            Icon(painterResource(id = R.drawable.ic_send),"")
        }
    }
}