package com.example.bitirmeprojesi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bitirmeprojesi.R

// Set of Material typography styles to start with
val Typography = Typography(

    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
val roboto_regular = Font(R.font.roboto_regular, FontWeight.Normal)
val roboto_medium = Font(R.font.roboto_medium, FontWeight.Medium)
val roboto_light = Font(R.font.roboto_light, FontWeight.Light)
val roboto_thin = Font(R.font.roboto_thin, FontWeight.Thin)
val roboto_italic = Font(R.font.roboto_italic, FontWeight.Normal) // Italic variant
val roboto_mediumitalic = Font(R.font.roboto_mediumitalic, FontWeight.Medium) // Medium Italic
val roboto_thinitalic = Font(R.font.roboto_thinitalic, FontWeight.Thin) // Thin Italic
