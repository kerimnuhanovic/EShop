package com.eshop.coreui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimensions(
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 64.dp,
    val logoSize: Dp = 200.dp,
    val errorBoxHeight: Dp = 60.dp,
    val mediumCornerRadius: Dp = 16.dp,
    val largeCornerRadius: Dp = 50.dp,
    val buttonHeight: Dp = 56.dp,
    val font_16: TextUnit = 16.sp,
    val font_20: TextUnit = 20.sp,
    val font_24: TextUnit = 24.sp,
    val font_32: TextUnit = 32.sp,
    val smallLetterSpacing: TextUnit = 1.sp
)

val LocalDimensions = compositionLocalOf { Dimensions() }