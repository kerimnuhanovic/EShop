package com.eshop.chat_presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.eshop.core.util.BASE_URL
import com.eshop.coreui.LocalDimensions

@Composable
fun ProfileImage(imageUrl: String, modifier: Modifier = Modifier) {
    val dimensions = LocalDimensions.current
    Card(
        shape = CircleShape,
        backgroundColor = MaterialTheme.colors.onSecondary,
        modifier = modifier,
        elevation = dimensions.spaceExtraSmall
    ) {
        AsyncImage(
            model = "$BASE_URL/${imageUrl}",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(dimensions.size_32)
                .clip(CircleShape)
        )
    }
}