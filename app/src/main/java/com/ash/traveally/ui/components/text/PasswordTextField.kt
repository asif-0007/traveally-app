package com.ash.traveally.ui.components.text

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Password
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String = "Password",
    onValueChange: (String) -> Unit,
    value: String = "",
    helperText: String,
    isError: Boolean = false
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    AppTextField(
        value = value,
        label = label,
        onValueChange = onValueChange,
        modifier = modifier,
        leadingIcon = { Icon(Icons.Outlined.Password, label) },
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = isError,
        helperText = helperText
    ) {
        val image = if (isPasswordVisible) {
            Icons.Filled.Visibility
        } else {
            Icons.Filled.VisibilityOff
        }
        val description = if (isPasswordVisible) "Hide password" else "Show password"

        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                imageVector = image,
                contentDescription = description,
                modifier = Modifier.testTag("TogglePasswordVisibility")
            )
        }
    }
}

