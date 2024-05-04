package com.eshop.chat_presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.theme.EShopTheme

@Composable
fun ChatScreenLoadingSkeleton() {
    val dimensions = LocalDimensions.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(dimensions.spaceMedium)) {
        MessageItemLoadingSkeleton(isMessageReceived = true, itemWidth = 0.5f)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = true)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = false)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = true)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = false)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = true)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = true)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = true)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = false)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = false)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = true)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = false)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
        MessageItemLoadingSkeleton(isMessageReceived = true)
        Spacer(modifier = Modifier.height(dimensions.spaceSmall))
    }
}

@Composable
@Preview(showBackground = true)
private fun ChatScreenLoadingSkeletonPreview() {
    EShopTheme {
        ChatScreenLoadingSkeleton()
    }
}