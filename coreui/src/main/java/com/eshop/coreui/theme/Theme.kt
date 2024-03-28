package com.eshop.coreui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.eshop.coreui.Dimensions
import com.eshop.coreui.LocalDimensions

private val DarkColorPalette = darkColorScheme(
    primary = Red,
    secondary = Blue,
    background = Color.White,
    onBackground = DarkGray,
    surface = LightGray,
    onSurface = DarkGray,
    onPrimary = Color.White,
    onSecondary = MediumGray,
)

private val LightColorPalette = lightColorScheme(
    primary = Red,
    secondary = Blue,
    background = Color.White,
    onBackground = DarkGray,
    surface = LightGray,
    onSurface = DarkGray,
    onPrimary = Color.White,
    onSecondary = MediumGray,
)

@Composable
fun EShopTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(LocalDimensions provides Dimensions()) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}