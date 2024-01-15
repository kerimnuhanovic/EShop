package com.eshop.coreui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.eshop.coreui.Dimensions
import com.eshop.coreui.LocalDimensions

private val DarkColorPalette = darkColors(
    primary = Red,
    primaryVariant = LightRed,
    secondary = Blue,
    background = LightGray,
    onBackground = DarkGray,
    surface = LightGray,
    onSurface = DarkGray,
    onPrimary = Color.White,
    onSecondary = MediumGray,
)

private val LightColorPalette = lightColors(
    primary = Red,
    primaryVariant = LightRed,
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
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}