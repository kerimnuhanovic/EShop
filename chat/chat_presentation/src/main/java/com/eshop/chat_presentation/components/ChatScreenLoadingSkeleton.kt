package com.eshop.chat_presentation.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.loadingAnimation

@Composable
fun ChatScreenLoadingSkeleton() {
    val dimensions = LocalDimensions.current

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colors.background)) {
        Column {
            repeat(10) {

                Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = dimensions.spaceMedium)
                            .size(dimensions.size_50)
                            .clip(CircleShape)
                            .loadingAnimation(
                                isLoading = true,
                                shimmerColor = MaterialTheme.colors.onSecondary
                            )
                    ) {}
                    Column(
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .clip(RoundedCornerShape(dimensions.size_12))
                                .defaultMinSize(minHeight = dimensions.size_12)
                                .loadingAnimation(
                                    isLoading = true,
                                    shimmerColor = MaterialTheme.colors.onSecondary
                                )
                        ) {}
                        Box(
                            modifier = Modifier
                                .padding(top = dimensions.spaceSmall)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(dimensions.size_12))
                                .defaultMinSize(minHeight = dimensions.size_12)
                                .loadingAnimation(
                                    isLoading = true,
                                    shimmerColor = MaterialTheme.colors.onSecondary
                                )
                        ) {}
                    }
                }
            }
        }
    }
}