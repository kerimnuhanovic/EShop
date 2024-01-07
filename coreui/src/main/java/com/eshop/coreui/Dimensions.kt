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
    val buttonHeight_56: Dp = 56.dp,
    val buttonHeight_50: Dp = 50.dp,
    val font_12: TextUnit = 12.sp,
    val font_14: TextUnit = 14.sp,
    val font_16: TextUnit = 16.sp,
    val font_20: TextUnit = 20.sp,
    val font_24: TextUnit = 24.sp,
    val font_32: TextUnit = 32.sp,
    val smallLetterSpacing: TextUnit = 1.sp,
    val uploadImageSurfaceSize: Dp = 250.dp,
    val uploadImagePlaceholderHeight: Dp = 200.dp,
    val uploadImagePlaceholderWidth: Dp = 150.dp,
    val offset_100: Dp = (-100).dp,
    val dropdownHeight: Dp = 150.dp,
    val size_20: Dp = 20.dp,
    val size_10: Dp = 10.dp,
    val size_32: Dp = 32.dp,
    val size_60: Dp = 60.dp,
    val size_50: Dp = 50.dp,
    val size_55: Dp = 55.dp,
    val size_80: Dp = 80.dp,
    val size_200: Dp = 200.dp,
    val floating_button_size: Dp = 56.dp,
    val size_150: Dp = 150.dp,
    val size_100: Dp = 100.dp,
    val size_90: Dp = 90.dp,
    val size_1: Dp = 1.dp,
    val offset_minus_10: Dp = (-10).dp
)

val LocalDimensions = compositionLocalOf { Dimensions() }