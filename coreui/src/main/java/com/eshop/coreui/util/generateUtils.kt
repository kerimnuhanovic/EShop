package com.eshop.coreui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import com.eshop.coreui.R
import com.eshop.coreui.navigation.Route

fun generateBottomBarItems(userType: String): List<BottomBarItem> {
    return when (userType) {
        "Customer" -> {
            listOf(
                BottomBarItem(text = "Products", icon = Icons.Rounded.List, route = Route.PRODUCTS_OVERVIEW),
                BottomBarItem(text = "Shops", iconId = R.drawable.shopping_basket_24, route = Route.SHOPS_OVERVIEW),
                BottomBarItem(text = "Message", iconId = R.drawable.message_24, route = Route.CONVERSATIONS),
                BottomBarItem(text = "Basket", icon = Icons.Rounded.ShoppingCart, route = Route.BASKET),
                BottomBarItem(text = "Dashboard", icon = Icons.Rounded.Settings, route = Route.DASHBOARD)
            )
        }
        "Shop" -> {
            listOf(
                BottomBarItem(text = "Products", icon = Icons.Rounded.List, route = Route.PRODUCTS_OVERVIEW),
                BottomBarItem(text = "Shops", iconId = R.drawable.shopping_basket_24, route = Route.SHOPS_OVERVIEW),
                BottomBarItem(text = "Message", iconId = R.drawable.message_24, route = Route.CONVERSATIONS),
                BottomBarItem(text = "Orders", iconId =R.drawable.list_alt_24, route = Route.ORDERS),
                BottomBarItem(text = "Dashboard", icon = Icons.Rounded.Settings, route = Route.DASHBOARD)
            )
        }
        else -> {
            throw IllegalArgumentException("Function is called with invalid argument!")
        }
    }
}