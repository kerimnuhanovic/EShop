package com.eshop.orders_presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.eshop.core.domain.models.Product
import com.eshop.core.util.BASE_URL
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import java.text.NumberFormat
import java.util.Currency

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit = {},
    currency: Currency = Currency.getInstance("EUR"),
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    val productDescriptionMaxLength = 200
    val threeDots = "..."
    val currencyFormat = remember {
        NumberFormat.getCurrencyInstance()
    }
    currencyFormat.currency = currency
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text(
                    text = product.title,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dimensions.font_16,
                    color = Color.Black
                )
                Text(
                    text = if (product.description.length < productDescriptionMaxLength) product.description else product.description.substring(
                        startIndex = 0,
                        endIndex = productDescriptionMaxLength - threeDots.length
                    ).plus(threeDots),
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = dimensions.font_12,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = currencyFormat.format(product.price), fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dimensions.font_12,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = "$BASE_URL/${product.images.first()}",
                contentDescription = null,
                modifier = Modifier
                    .size(dimensions.size_80)
                    .clip(RoundedCornerShape(CornerSize(dimensions.size_12))),
                contentScale = ContentScale.Crop
            )
        }
        Divider(
            color = MaterialTheme.colors.onSecondary.copy(0.5f),
            modifier = Modifier
                .height(dimensions.size_1)
                .fillMaxWidth()
        )
    }
}