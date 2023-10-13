package com.eshop.signup_presentation.signup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.theme.Red

@Composable
fun PageIndicator(currentPage: Int = 0, modifier: Modifier = Modifier) {
    val dimensions = LocalDimensions.current
    Row(
        modifier = modifier
            .height(dimensions.size_20)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(3) { iteration ->
            val color = if (currentPage == iteration) Red else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(dimensions.spaceExtraSmall)
                    .clip(CircleShape)
                    .background(color)
                    .size(dimensions.size_10)
            )
        }
    }

}