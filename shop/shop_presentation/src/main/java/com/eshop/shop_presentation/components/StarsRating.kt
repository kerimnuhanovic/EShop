package com.eshop.shop_presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.theme.StarGreenVariant

@Composable
fun StarsRating(
    rating: Int,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    Row(modifier = modifier) {
        repeat(rating) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = StarGreenVariant,
                modifier = Modifier.size(dimensions.spaceMedium)
            )
        }
        repeat(5 - rating) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = Color.Gray.copy(0.6f),
                modifier = Modifier.size(dimensions.spaceMedium)
            )
        }
    }
}