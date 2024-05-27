package com.eshop.cart_presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.eshop.cart_presentation.components.CartItem
import com.eshop.cart_presentation.components.CartScreenLoadingSkeleton
import com.eshop.core.domain.models.Product
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.components.BottomBar
import com.eshop.coreui.components.EShopButton
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.MediumGrayVariant
import com.eshop.coreui.util.BottomBarItem
import com.eshop.coreui.util.UiEvent
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Currency

@Composable
fun CartScreen(viewModel: CartViewModel = hiltViewModel(), onNavigate: (UiEvent.Navigate) -> Unit) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            if (uiEvent is UiEvent.Navigate) {
                onNavigate(uiEvent)
            }
            if (uiEvent is UiEvent.DisplayToast) {
                Toast.makeText(context, uiEvent.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    CartScreenContent(state = state, onEvent = viewModel::onEvent, onNavigate = onNavigate)

}

@Composable
private fun CartScreenContent(
    state: CartState,
    onEvent: (CartEvent) -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val dimensions = LocalDimensions.current
    val currencyFormat = remember {
        NumberFormat.getCurrencyInstance()
    }
    currencyFormat.currency = Currency.getInstance("EUR")

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomBarItem(
                        text = "Products",
                        icon = Icons.Rounded.List,
                        route = Route.PRODUCTS_OVERVIEW
                    ),
                    BottomBarItem(
                        text = "Shops",
                        iconId = R.drawable.shopping_basket_24,
                        route = Route.SHOPS_OVERVIEW
                    ),
                    BottomBarItem(
                        text = "Message",
                        iconId = R.drawable.message_24,
                        route = Route.CONVERSATIONS
                    ),
                    BottomBarItem(
                        text = "Basket",
                        icon = Icons.Rounded.ShoppingCart,
                        route = Route.BASKET
                    ),
                    BottomBarItem(
                        text = "Dashboard",
                        icon = Icons.Rounded.Settings,
                        route = Route.DASHBOARD
                    )
                ),
                isBottomBarOverlapped = false,
                onNavigate = onNavigate,
                currentDestination = Route.BASKET,
            )
        }
    ) {
        if (state.isLoading) {
            CartScreenLoadingSkeleton()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(
                        bottom = it.calculateBottomPadding(),
                        start = dimensions.spaceMedium,
                        end = dimensions.spaceMedium
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                Text(
                    text = stringResource(id = com.eshop.cart_presentation.R.string.my_cart),
                    fontFamily = PoppinsFontFamily,
                    fontSize = dimensions.font_16,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                state.products.forEachIndexed { index, product ->
                    CartItem(product = product, onDeleteClick = {
                        onEvent(CartEvent.OnDeleteCartItem(itemIndex = index, productId = product.id))
                    })
                    Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                }
                if (state.products.isEmpty()) {
                    Text(
                        text = stringResource(id = com.eshop.cart_presentation.R.string.your_cart_is_empty),
                        fontFamily = PoppinsFontFamily,
                        fontSize = dimensions.font_16,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = com.eshop.cart_presentation.R.string.items),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_14,
                        color = MediumGrayVariant
                    )
                    Text(
                        text = state.products.size.toString(),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_16,
                        color = MediumGrayVariant
                    )
                }
                Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = com.eshop.cart_presentation.R.string.delivery_charge),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_14,
                        color = MediumGrayVariant
                    )
                    Text(
                        text = currencyFormat.format(state.deliveryCharge),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_16,
                        color = MediumGrayVariant
                    )
                }
                Spacer(modifier = Modifier.height(dimensions.spaceSmall))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = com.eshop.cart_presentation.R.string.sub_total),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_14,
                        color = MediumGrayVariant
                    )
                    Text(
                        text = currencyFormat.format(state.subTotal),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_16,
                        color = MediumGrayVariant
                    )
                }
                Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                Divider()
                Spacer(modifier = Modifier.height(dimensions.spaceLarge))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = com.eshop.cart_presentation.R.string.total),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_20,
                        color = Color.Black
                    )
                    Text(
                        text = currencyFormat.format(state.total),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = dimensions.font_20,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                EShopButton(
                    content = {
                        if (state.isOrderSubmitting) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.onPrimary,
                                modifier = Modifier.size(dimensions.size_32)
                            )
                        } else {
                            Text(
                                text = stringResource(id = com.eshop.cart_presentation.R.string.submit_order),
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = dimensions.font_16
                            )
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                    shape = RoundedCornerShape(dimensions.spaceSmall),
                    onButtonClick = { onEvent(CartEvent.OnOrderSubmit) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isOrderSubmitting && state.products.isNotEmpty()
                )
                Spacer(modifier = Modifier.height(dimensions.spaceMedium))
            }
        }
    }
}

@Composable
@Preview
private fun CartScreenPreview() {
    EShopTheme {
        CartScreenContent(state = CartState(
            products = listOf(
                Product(
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
        ), onEvent = {}, onNavigate = {})
    }
}