package com.eshop.e_shop.components

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eshop.core.navigation.Route
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.util.UiEvent
import com.eshop.e_shop.util.BottomBarItem
import com.eshop.e_shop.util.checkIsProductRoute

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomBar(
    items: List<BottomBarItem>,
    onNavigate: (UiEvent.Navigate) -> Unit,
    navController: NavController,
    isBottomBarOverlapped: Boolean
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val dimensions = LocalDimensions.current
    if(!Route.listLandingRoutes().contains(currentDestination) && !checkIsProductRoute(currentDestination) && !isBottomBarOverlapped) {
        BottomAppBar(
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

@OptIn(ExperimentalMaterialApi::class)
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
            navController = rememberNavController(),
            isBottomBarOverlapped = false
        )
    }
}