package com.ash.traveally.ui.components.text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ash.traveally.ui.theme.LightGreen
import com.ash.traveally.ui.theme.MontserratAlternates


@Composable
fun AppTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    helperText: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Surface(modifier = modifier) {
        Column {
            OutlinedTextField(
                value = value,
                label = { Text(text = label, fontSize = 14.sp, fontFamily = MontserratAlternates, fontWeight = FontWeight.W500) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(label),
                onValueChange = onValueChange,
                leadingIcon = leadingIcon,
                textStyle = TextStyle(fontSize = 15.sp, fontFamily = MontserratAlternates),
                isError = isError,
                visualTransformation = visualTransformation,
                shape = RoundedCornerShape(8.dp),
                trailingIcon = trailingIcon,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LightGreen, focusedLabelColor = LightGreen)
            )
            if (helperText.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = helperText,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = MontserratAlternates,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}
