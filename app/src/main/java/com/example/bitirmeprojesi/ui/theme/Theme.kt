package com.example.bitirmeprojesi.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext



// Letterboxd-inspired dark color scheme
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00B021), // Letterboxd green
    secondary = Color(0xFFF27405), // Gold/yellow
    tertiary = Color(0xFF556678), // Neutral gray for accents
    background = Color(0xFF2C343F), // Dark background
    surface = Color(0xFF1C1C1C), // Slightly lighter for cards
    onPrimary = Color.White, // Text/icons on green
    onSecondary = Color.Black, // Text/icons on yellow
    onBackground = Color.White, // Text on dark background
    onSurface = Color.White // Text on surfaces
)

// Light color scheme (optional, as Letterboxd primarily uses dark themes)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00A56A), // Same green
    secondary = Color(0xFFFFD700), // Same yellow
    tertiary = Color(0xFF606060), // Neutral gray for accents
    background = Color(0xFFFFFFFF), // Light background
    surface = Color(0xFFF2F2F2), // Lighter surface
    onPrimary = Color.White, // Text/icons on green
    onSecondary = Color.Black, // Text/icons on yellow
    onBackground = Color.Black, // Text on light background
    onSurface = Color.Black // Text on surfaces
)

@Composable
fun BitirmeProjesiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
