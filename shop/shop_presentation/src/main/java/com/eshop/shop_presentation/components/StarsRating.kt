package com.eshop.shop_presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eshop.coreui.theme.StarGreenVariant

@Composable
fun StarsRating(
    rating: Int,
    onStarClick: (Int) -> Unit = {},
    starSize: Dp = 16.dp,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(rating) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = StarGreenVariant,
                modifier = Modifier.size(starSize).clickable {
                    onStarClick(it + 1)
                }
            )
        }
        repeat(5 - rating) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = Color.Gray.copy(0.6f),
                modifier = Modifier.size(starSize).clickable {
                    onStarClick(rating + it + 1)
                }
            )
        }
    }
}