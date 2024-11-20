package com.ash.traveally.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ash.traveally.R

// Set of Material typography styles to start with
val MontserratAlternates = FontFamily(
    Font(R.font.montserrat_alternates_light, FontWeight.Light),
    Font(R.font.montserrat_alternates_regular, FontWeight.Normal),
    Font(R.font.montserrat_alternates_medium, FontWeight.Medium),
    Font(R.font.montserrat_alternates_bold, FontWeight.Bold)
)
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
)