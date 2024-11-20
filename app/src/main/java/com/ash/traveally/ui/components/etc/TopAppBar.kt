package com.ash.traveally.ui.components.etc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ash.traveally.R
import com.ash.traveally.ui.theme.MontserratAlternates

@Composable
fun TopAppBar() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .padding(16.dp)
        )
        Text(
            text = "traveally",
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.W500
            ),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}