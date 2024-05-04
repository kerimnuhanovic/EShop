package com.eshop.chat_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.util.loadingAnimation

@Composable
fun MessageItemLoadingSkeleton(isMessageReceived: Boolean, itemWidth: Float = 0.8f) {
    val dimensions = LocalDimensions.current

    val shape = if (isMessageReceived) RoundedCornerShape(
        topStart = dimensions.default,
        topEnd = dimensions.spaceMedium,
        bottomEnd = dimensions.default,
        bottomStart = dimensions.spaceMedium
    ) else RoundedCornerShape(
        topStart = dimensions.spaceMedium,
        topEnd = dimensions.default,
        bottomEnd = dimensions.spaceMedium,
        bottomStart = dimensions.default
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (!isMessageReceived) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(itemWidth)
                .clip(shape = shape)
                .defaultMinSize(minHeight = dimensions.size_32)
                .loadingAnimation(
                    isLoading = true,
                    shimmerColor = MaterialTheme.colors.onSecondary
                )
        ) {

        }
    }
}