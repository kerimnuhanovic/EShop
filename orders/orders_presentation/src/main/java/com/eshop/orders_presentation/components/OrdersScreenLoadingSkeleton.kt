package com.eshop.orders_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.util.loadingAnimation

@Composable
fun OrdersScreenLoadingSkeleton() {
    val dimensions = LocalDimensions.current

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colors.background)
        .padding(dimensions.spaceMedium)) {
        items(10) {
            Card(
                shape = RoundedCornerShape(dimensions.size_12),
                elevation = dimensions.spaceSmall,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.background
            ) {
                Row(modifier = Modifier.padding(dimensions.spaceSmall)) {
                    Box(
                        modifier = Modifier
                            .size(dimensions.size_80)
                            .clip(RoundedCornerShape(CornerSize(dimensions.size_12)))
                            .loadingAnimation(
                                isLoading = true,
                                shimmerColor = MaterialTheme.colors.onSecondary
                            )
                    ) {}
                    Spacer(modifier = Modifier.width(dimensions.spaceMedium))
                    Column(
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .clip(RoundedCornerShape(dimensions.size_12))
                                .defaultMinSize(minHeight = dimensions.spaceMedium)
                                .loadingAnimation(
                                    isLoading = true,
                                    shimmerColor = MaterialTheme.colors.onSecondary
                                )
                        ) {}
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .clip(RoundedCornerShape(dimensions.size_12))
                                .defaultMinSize(minHeight = dimensions.spaceMedium)
                                .loadingAnimation(
                                    isLoading = true,
                                    shimmerColor = MaterialTheme.colors.onSecondary
                                )
                        ) {}
                    }
                }
            }
            Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        }
    }
}