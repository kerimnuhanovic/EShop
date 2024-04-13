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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
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
                        Icon(imageVector = item.icon, contentDescription = null)
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
                BottomBarItem("Products", Icons.Default.Home, "products"),
                BottomBarItem("Shops", Icons.Default.Place, "shops"),
                BottomBarItem("Basket", Icons.Default.ShoppingCart, "basket"),
                BottomBarItem("Orders", Icons.Default.List, "orders")
            ),
            onNavigate = {},
            "Home",
            isBottomBarOverlapped = false
        )
    }
}