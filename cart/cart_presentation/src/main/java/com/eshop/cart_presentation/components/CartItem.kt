package com.eshop.cart_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.eshop.core.domain.models.Product
import com.eshop.core.util.BASE_URL
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.EShopTheme
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Currency

@Composable
fun CartItem(
    product: Product,
    currency: Currency = Currency.getInstance("EUR")
) {
    val dimensions = LocalDimensions.current
    val currencyFormat = remember {
        NumberFormat.getCurrencyInstance()
    }
    currencyFormat.currency = currency

    Card(
        shape = RoundedCornerShape(dimensions.size_12),
        elevation = dimensions.spaceExtraSmall,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(dimensions.spaceSmall)) {
            Row {
                AsyncImage(
                    model = "$BASE_URL/${product.images.first()}",
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensions.size_80)
                        .clip(RoundedCornerShape(CornerSize(dimensions.size_12))),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(dimensions.spaceMedium))
                Column(
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = product.title,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_14,
                        color = Color.Black
                    )
                    Text(
                        text = currencyFormat.format(product.price), fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_12,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CartItemPreview() {
    EShopTheme {
        CartItem(
            product = Product(
                id = "product_od",
                title = "Product title",
                description = "Product description",
                category = listOf("Product category"),
                date = LocalDate.now(),
                images = listOf("imageurl"),
                price = 200.00,
                shop = "Shop"
            )
        )
    }
}