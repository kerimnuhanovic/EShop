package com.eshop.shop_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.util.loadingAnimation

@Composable
fun ShopScreenLoadingSkeleton() {
    val dimensions = LocalDimensions.current
    Column {
        Box(
            modifier = Modifier
                .height(dimensions.size_250)
                .fillMaxWidth()
                .loadingAnimation(
                    isLoading = true,
                    shimmerColor = MaterialTheme.colorScheme.onSecondary
                )
        ) {}
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        Box(
            modifier = Modifier
                .defaultMinSize(minWidth = dimensions.size_200, minHeight = dimensions.size_24)
                .padding(horizontal = dimensions.spaceMedium)
                .loadingAnimation(
                    isLoading = true,
                    shimmerColor = MaterialTheme.colorScheme.onSecondary
                )
        ) {}
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        Box(
            modifier = Modifier
                .padding(
                    horizontal = dimensions.spaceMedium
                )
                .fillMaxWidth()
                .defaultMinSize(minHeight = dimensions.size_20)
                .loadingAnimation(
                    isLoading = true,
                    shimmerColor = MaterialTheme.colorScheme.onSecondary
                )
        ) {}
        Spacer(modifier = Modifier.height(dimensions.spaceExtraLarge))
        Box(
            modifier = Modifier
                .padding(
                    horizontal = dimensions.spaceMedium
                )
                .fillMaxWidth()
                .defaultMinSize(minHeight = dimensions.size_20)
                .loadingAnimation(
                    isLoading = true,
                    shimmerColor = MaterialTheme.colorScheme.onSecondary
                )
        ) {}
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        Box(
            modifier = Modifier
                .padding(
                    horizontal = dimensions.spaceMedium
                )
                .fillMaxWidth()
                .defaultMinSize(minHeight = dimensions.size_20)
                .loadingAnimation(
                    isLoading = true,
                    shimmerColor = MaterialTheme.colorScheme.onSecondary
                )
        ) {}
        Spacer(modifier = Modifier.height(dimensions.size_100))
        Box(
            modifier = Modifier
                .padding(
                    horizontal = dimensions.spaceMedium
                )
                .fillMaxWidth()
                .weight(1f)
                .loadingAnimation(
                    isLoading = true,
                    shimmerColor = MaterialTheme.colorScheme.onSecondary
                )
        ) {}
    }
}