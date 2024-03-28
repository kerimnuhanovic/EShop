package com.eshop.coreui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.R
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.MediumGray


@Composable
fun ImageUploadPlaceholder(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current
    val borderColor = MaterialTheme.colorScheme.onBackground
    val stroke = Stroke(
        width = 16f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
    )
    Surface(
        modifier = modifier
            .height(dimensions.size_100)
            .width(dimensions.size_100),
        color = MediumGray,
        shape = RoundedCornerShape(CornerSize(dimensions.spaceExtraSmall))
    ) {
        Surface(
            modifier = Modifier
                .padding(dimensions.spaceSmall)
                .drawBehind {
                    drawRoundRect(
                        color = borderColor,
                        style = stroke,
                        cornerRadius = CornerRadius(dimensions.spaceExtraSmall.toPx())
                    )
                },
            color = MediumGray,
        ) {
            Image(
                modifier = Modifier.clickable {
                    onClick()
                },
                painter = painterResource(id = R.drawable.add_photo_dark_gray),
                contentDescription = null,
                contentScale = ContentScale.None
            )
        }
    }
}

@Preview
@Composable
private fun ImageUploadPlaceholderPreview() {
    EShopTheme {
        ImageUploadPlaceholder(onClick = { })
    }
}