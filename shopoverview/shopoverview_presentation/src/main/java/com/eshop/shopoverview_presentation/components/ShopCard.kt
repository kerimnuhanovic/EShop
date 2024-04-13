package com.eshop.shopoverview_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.StarGreenVariant

@Composable
fun ShopCard(
    image: String,
    shopName: String,
    location: String,
    review: Double,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    val shopNameMaxLength = 35
    val threeDots = "..."
    Card(
        backgroundColor = MaterialTheme.colors.onSecondary,
        elevation = dimensions.spaceSmall,
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(CornerSize(dimensions.spaceSmall))
    ) {
        Column {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight(0.4f)
            )
            Spacer(modifier = Modifier.height(dimensions.spaceSmall))
            Text(
                text = if (shopName.length < shopNameMaxLength) shopName else shopName.substring(
                    startIndex = 0,
                    endIndex = shopNameMaxLength - threeDots.length
                ).plus(threeDots),
                fontSize = dimensions.font_16,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(horizontal = dimensions.spaceSmall)
            )
            Spacer(modifier = Modifier.height(dimensions.spaceExtraSmall))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(horizontal = dimensions.spaceExtraSmall)
                )
                Text(
                    text = location,
                    fontSize = dimensions.font_12,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(end = dimensions.spaceExtraSmall)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = dimensions.spaceExtraSmall)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = StarGreenVariant,
                    modifier = Modifier
                        .padding(horizontal = dimensions.spaceExtraSmall)
                )
                Text(
                    text = review.toString(),
                    fontSize = dimensions.font_12,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(end = dimensions.spaceExtraSmall)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
@Preview
private fun ProductCardPreview() {
    val dimensions = LocalDimensions.current
    EShopTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            ShopCard(
                image = "https://upload.wikimedia.org/wikipedia/commons/d/d7/Mostar_Old_Town_Panorama_2007.jpg",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(dimensions.uploadImageSurfaceSize),
                shopName = "Shop name",
                location = "Shop Location",
                review = 4.4,
                onClick = {}
            )
        }
    }
}