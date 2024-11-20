package com.ash.traveally.ui.components.text

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ash.traveally.ui.components.buttons.SendButton
import com.ash.traveally.ui.theme.LightGreen
import com.ash.traveally.ui.theme.MontserratAlternates

@Composable
fun MessageTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onMessageChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row (modifier = modifier) {
        OutlinedTextField(
            value = value,
            label = { Text(text = "Message", fontSize = 14.sp, fontFamily = MontserratAlternates, fontWeight = FontWeight.W500) },
            modifier = Modifier
                .weight(1f)
                .testTag("Message"),
            onValueChange = onMessageChange,
            textStyle = TextStyle(fontSize = 15.sp, fontFamily = MontserratAlternates),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LightGreen, focusedLabelColor = LightGreen)
        )
        SendButton(modifier = Modifier.padding(top = 10.dp), onClick = onSend)
    }
}
