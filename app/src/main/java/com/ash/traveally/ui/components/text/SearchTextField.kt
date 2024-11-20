package com.ash.traveally.ui.components.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ash.traveally.R
import com.ash.traveally.ui.theme.LightGreen
import com.ash.traveally.ui.theme.MontserratAlternates

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    placeholderText: String = "Search",
    onClearClick: () -> Unit
) {
    OutlinedTextField(
        value = searchText,
        label = { Text(text = placeholderText, fontSize = 14.sp, fontFamily = MontserratAlternates, fontWeight = FontWeight.W500) },
        modifier = modifier
            .fillMaxWidth(),
        onValueChange = onSearchTextChanged,
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = null)
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = onClearClick) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear text")
                }
            }
        },
        textStyle = TextStyle(fontSize = 16.sp, fontFamily = MontserratAlternates),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LightGreen, focusedLabelColor = LightGreen),
        singleLine = true,
        maxLines = 1
    )
}