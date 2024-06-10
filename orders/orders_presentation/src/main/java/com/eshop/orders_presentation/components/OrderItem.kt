package com.eshop.orders_presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.core.domain.models.OrderDetailsWithProducts
import com.eshop.core.domain.models.Product
import com.eshop.core.util.OrderStatus
import com.eshop.core.util.UserType
import com.eshop.core.util.formatDate
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.components.EShopButton
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.MediumOrange
import com.eshop.coreui.theme.StarGreenVariant
import com.eshop.orders_presentation.R
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Currency

@Composable
fun OrderItem(
    order: OrderDetailsWithProducts,
    userType: UserType,
    dateCreated: LocalDate,
    isExpanded: Boolean,
    onOrderClick: () -> Unit,
    modifier: Modifier = Modifier,
    customerUsername: String = "",
    onAcceptClick: () -> Unit = {},
    onDeclineClick: () -> Unit = {},
    isDeclineStatusSubmitting: Boolean = false,
    isAcceptStatusSubmitting: Boolean = false
) {
    val dimensions = LocalDimensions.current
    val currencyFormat = remember {
        NumberFormat.getCurrencyInstance()
    }
    currencyFormat.currency = Currency.getInstance("EUR")
    val totalPrice = currencyFormat.format(order.items.sumOf { it.price })

    Card(
        shape = RoundedCornerShape(dimensions.size_12),
        elevation = dimensions.spaceExtraSmall,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensions.spaceMedium),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(dimensions.spaceSmall)
                    .fillMaxWidth()
                    .clickable {
                        onOrderClick()
                    }
            ) {
                Column {
                    Text(
                        text = "#${order.id?.substring(0, 10)}",
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_16,
                        color = Color.Black
                    )
                    Text(
                        text = if (userType is UserType.Shop) customerUsername else order.shop,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_14,
                        color = MaterialTheme.colors.primary
                    )
                    Text(
                        text = "Items: ${order.items.size}",
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_12,
                    )
                    Text(
                        text = dateCreated.formatDate(),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_12
                    )
                    Text(
                        text = "Total: $totalPrice", fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_14,
                        color = Color.Black
                    )
                }
                when (order.status) {
                    OrderStatus.Approved -> {
                        Icon(
                            painter = painterResource(id = R.drawable.check_circle_outline_24),
                            contentDescription = null,
                            tint = StarGreenVariant
                        )
                    }

                    OrderStatus.Declined -> {
                        Icon(
                            painter = painterResource(id = R.drawable.clear_24),
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }

                    OrderStatus.Pending -> {
                        Icon(
                            painter = painterResource(id = R.drawable.dash_icon),
                            contentDescription = null,
                            tint = MediumOrange
                        )
                    }
                }
            }
            AnimatedVisibility(visible = isExpanded) {
                Divider()
                Column {
                    order.items.map {
                        ProductItem(
                            product = it,
                            modifier = Modifier.padding(dimensions.spaceSmall)
                        )
                    }
                    if (userType is UserType.Shop) {
                        Row {
                            EShopButton(
                                content = {
                                    if (isDeclineStatusSubmitting) {
                                        CircularProgressIndicator(
                                            color = MaterialTheme.colors.onPrimary,
                                            modifier = Modifier.size(dimensions.size_32)
                                        )
                                    }
                                    else {
                                        Text(text = stringResource(id = R.string.decline_order))
                                    }
                                },
                                backgroundColor = MaterialTheme.colors.onSecondary,
                                contentColor = Color.Black,
                                shape = RectangleShape,
                                onButtonClick = { onDeclineClick() },
                                enabled = !isDeclineStatusSubmitting,
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .padding(dimensions.spaceSmall)
                            )
                            EShopButton(
                                content = {
                                    if (isAcceptStatusSubmitting) {
                                        CircularProgressIndicator(
                                            color = MaterialTheme.colors.onPrimary,
                                            modifier = Modifier.size(dimensions.size_32)
                                        )
                                    }
                                    else {
                                        Text(text = stringResource(id = R.string.accept_order))
                                    }
                                },
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = Color.White,
                                shape = RectangleShape,
                                onButtonClick = { onAcceptClick() },
                                enabled = !isAcceptStatusSubmitting,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = dimensions.spaceSmall,
                                        bottom = dimensions.spaceSmall,
                                        end = dimensions.spaceSmall
                                    )
                            )
                        }
                    }
                }
            }
        }
    }


}

@Preview
@Composable
private fun OrderItemPreview() {
    EShopTheme {
        OrderItem(
            order = OrderDetailsWithProducts(
                shop = "Inoma",
                items = listOf(
                    Product(
                        id = "659f18c8fdd9f72ed811e907",
                        title = "Isra & Miraj Stephane Humber Lucas",
                        description = "Description",
                        category = listOf("Apparel and Fashion"),
                        price = 365.00,
                        shop = "Inoma",
                        date = LocalDate.now(),
                        images = listOf("image.jpg")
                    ),
                    Product(
                        id = "659f18c8fdd9f72ed811e907",
                        title = "Azzaro The Most Wanted EDP",
                        description = "Description",
                        category = listOf("Apparel and Fashion"),
                        price = 365.00,
                        shop = "Inoma",
                        date = LocalDate.now(),
                        images = listOf("image.jpg")
                    )
                ),
                status = OrderStatus.Pending,
                id = "663e6b6ef76e62c895d8a32c"
            ),
            dateCreated = LocalDate.now(),
            isExpanded = true,
            onOrderClick = {},
            userType = UserType.Shop,
            customerUsername = "knuhanovic"
        )
    }
}