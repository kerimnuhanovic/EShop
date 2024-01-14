package com.eshop.coreui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import com.eshop.coreui.LocalDimensions

@Composable
fun ProductCardPlaceholderRow(
    numberOfItems: Int = 3
) {
    val dimensions = LocalDimensions.current
    LazyRow {
        items(numberOfItems) { key ->
            ProductCardPlaceholder(
                modifier = androidx.compose.ui.Modifier
                    .width(dimensions.card_width_180)
                    .height(dimensions.uploadImageSurfaceSize)
                    .padding(
                        top = dimensions.spaceSmall,
                        bottom = dimensions.spaceSmall,
                        start = if (key != 0) dimensions.spaceSmall else dimensions.spaceMedium,
                        end = if (key != 2) dimensions.default else dimensions.spaceMedium
                    )
            )
        }
    }
}