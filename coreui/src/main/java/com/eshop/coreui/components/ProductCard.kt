package com.eshop.coreui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.loadingAnimation
import java.text.NumberFormat
import java.util.Currency

@Composable
fun ProductCard(
    image: String,
    productName: String,
    price: Double,
    date: String,
    currency: Currency = Currency.getInstance("EUR"),
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    val currencyFormat = remember {
        NumberFormat.getCurrencyInstance()
    }
    val productNameMaxLength = 35
    val threeDots = "..."
    currencyFormat.currency = currency
    Card(
        backgroundColor = MaterialTheme.colors.onSecondary,
        elevation = dimensions.spaceSmall,
        modifier = modifier,
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
                text = if (productName.length < productNameMaxLength) productName else productName.substring(
                    startIndex = 0,
                    endIndex = productNameMaxLength - threeDots.length
                ).plus(threeDots),
                fontSize = dimensions.font_16,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(horizontal = dimensions.spaceSmall)
            )
            Spacer(modifier = Modifier.height(dimensions.spaceExtraSmall))
            Text(
                text = date,
                fontSize = dimensions.font_12,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(horizontal = dimensions.spaceSmall)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = currencyFormat.format(price),
                    fontSize = dimensions.font_20,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(horizontal = dimensions.spaceSmall)
                )
            }
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
                .padding(dimensions.spaceMedium)
                .background(MaterialTheme.colors.background)
        ) {
            ProductCard(
                image = "https://upload.wikimedia.org/wikipedia/commons/d/d7/Mostar_Old_Town_Panorama_2007.jpg",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(dimensions.uploadImageSurfaceSize),
                productName = "Samsung Galaxy S23",
                price = 20.0,
                date = "20.9.2023"
            )
        }
    }
}