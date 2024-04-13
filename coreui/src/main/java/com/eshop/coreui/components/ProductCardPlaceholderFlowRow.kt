package com.eshop.coreui.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eshop.coreui.LocalDimensions

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductCardPlaceholderFlowRow(
    numberOfItems: Int = 20
) {
    val dimensions = LocalDimensions.current
    FlowRow(modifier = Modifier.padding(horizontal = dimensions.spaceMedium)) {
        for (i in 0 until numberOfItems) {
            ProductCardPlaceholder(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(dimensions.uploadImageSurfaceSize)
                    .padding(
                        top = dimensions.spaceSmall,
                        bottom = dimensions.spaceSmall,
                        start = if (i % 2 == 0) dimensions.default else dimensions.spaceSmall
                    ))
        }
    }
}