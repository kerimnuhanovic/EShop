package com.eshop.userdashboard_presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import com.eshop.coreui.navigation.Route
import com.eshop.userdashboard_presentation.R

sealed class DashboardItem(val label: String, val icon: ImageVector? = null, val iconId: Int? = null, val route: String) {
    object FavouriteProducts : DashboardItem(label = "Favourite Products", icon = Icons.Rounded.Favorite, route = Route.FAVOURITE_PRODUCTS)
    object FavouriteShops : DashboardItem(label = "Favourite Shops", icon = Icons.Rounded.Favorite, route = Route.FAVOURITE_SHOPS)
    object MyOrders : DashboardItem(label = "My Orders", iconId = R.drawable.list_alt_24, route = Route.ORDERS)
    object LogOut : DashboardItem(label = "Log out", iconId = R.drawable.logout_24, route = Route.LOGIN)
}