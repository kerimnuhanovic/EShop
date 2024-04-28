package com.eshop.coreui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.R
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.BottomBarItem
import com.eshop.coreui.util.UiEvent

@Composable
fun BottomBar(
    items: List<BottomBarItem>,
    onNavigate: (UiEvent.Navigate) -> Unit,
    currentDestination: String?,
    isBottomBarOverlapped: Boolean,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    if (!isBottomBarOverlapped) {
        BottomAppBar(
            modifier = modifier.height(dimensions.size_56),
            backgroundColor = MaterialTheme.colors.onPrimary,
            elevation = dimensions.spaceExtraSmall
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    selected = currentDestination == item.route,
                    onClick = { onNavigate(UiEvent.Navigate(item.route)) },
                    icon = {
                        if (item.iconId !== null) {
                            Icon(painter = painterResource(id = item.iconId), contentDescription = null)
                        } else {
                            Icon(imageVector = item.icon!!, contentDescription = null)
                        }
                    },
                    label = {
                        Text(text = item.text)
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.surface
                )
            }
        }
    }
}

@Composable
@Preview
private fun BottomBarPreview() {
    EShopTheme {
        BottomBar(
            items = listOf(
                BottomBarItem(text = "Products", icon = Icons.Rounded.List, route = "products"),
                BottomBarItem(text = "Shops", route = "shops", iconId = R.drawable.shopping_basket_24),
                BottomBarItem(text = "Message", route = "messages", iconId = R.drawable.message_24),
                BottomBarItem(text = "Cart", icon = Icons.Rounded.ShoppingCart, route = "cart"),
                BottomBarItem(text = "Orders", icon = Icons.Rounded.Settings, route = "orders")
            ),
            onNavigate = {},
            "messages",
            isBottomBarOverlapped = false
        )
    }
}