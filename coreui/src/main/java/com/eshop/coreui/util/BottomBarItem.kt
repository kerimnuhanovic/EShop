package com.eshop.coreui.util

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
    val text: String,
    val route: String,
    val icon: ImageVector? = null,
    val iconId: Int? = null
)