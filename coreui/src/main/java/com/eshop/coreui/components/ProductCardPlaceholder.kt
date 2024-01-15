package com.eshop.coreui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.util.loadingAnimation

@Composable
fun ProductCardPlaceholder(
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    Card(
        backgroundColor = MaterialTheme.colors.onSecondary,
        elevation = dimensions.spaceSmall,
        modifier = modifier,
        shape = RoundedCornerShape(CornerSize(dimensions.spaceSmall))
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxHeight(0.4f).fillMaxWidth().loadingAnimation(isLoading = true)
            )
            Spacer(modifier = Modifier.height(dimensions.spaceSmall))
            Box(
                modifier = Modifier.padding(horizontal = dimensions.spaceSmall).fillMaxWidth(0.5f).height(dimensions.spaceMedium).loadingAnimation(isLoading = true)
            )
            Spacer(modifier = Modifier.height(dimensions.spaceExtraSmall))
            Box(
                modifier = Modifier.padding(horizontal = dimensions.spaceSmall).fillMaxWidth(0.2f).height(dimensions.size_12).loadingAnimation(isLoading = true)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = dimensions.spaceSmall),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = dimensions.spaceSmall).fillMaxWidth(0.5f).height(dimensions.size_20).loadingAnimation(isLoading = true)
                )
            }
        }
    }
}