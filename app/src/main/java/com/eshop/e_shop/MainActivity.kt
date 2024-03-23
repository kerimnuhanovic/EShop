package com.eshop.e_shop

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eshop.core.navigation.Route
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.components.FloatingButton
import com.eshop.coreui.theme.EShopTheme
import com.eshop.e_shop.components.BottomBar
import com.eshop.e_shop.navigation.navigate
import com.eshop.e_shop.navigation.navigateBack
import com.eshop.e_shop.util.BottomBarItem
import com.eshop.login_presentation.login.LoginScreen
import com.eshop.product_presentation.ProductScreen
import com.eshop.productoverview_presentation.ProductOverviewScreen
import com.eshop.shop_presentation.ShopScreen
import com.eshop.shopoverview_presentation.ShopOverviewScreen
import com.eshop.signup_presentation.signup.SignupScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EShopTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val isBottomBarOverlapped = remember {
                    mutableStateOf(false)
                }
                val bottomSheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    confirmValueChange = {
                        if (it == ModalBottomSheetValue.Hidden)
                            isBottomBarOverlapped.value = false
                        true
                    },
                    skipHalfExpanded = true
                )

                val dimensions = LocalDimensions.current
                val bottomBarHeightPx = with(LocalDensity.current) { dimensions.size_56.roundToPx().toFloat() }
                val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
                val topBarOffsetHeightPx = remember { mutableStateOf(0f) }
                val nestedScrollConnection = remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                            val delta = available.y
                            val newOffset = bottomBarOffsetHeightPx.value + delta
                            bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                            topBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)

                            return Offset.Zero
                        }
                    }
                }


                Scaffold(
                    modifier = Modifier.fillMaxSize().nestedScroll(nestedScrollConnection),
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomBar(
                            items = listOf(
                                BottomBarItem("Products", Icons.Rounded.Home, Route.PRODUCTS_OVERVIEW),
                                BottomBarItem("Shops", Icons.Rounded.Place, Route.SHOPS_OVERVIEW),
                                BottomBarItem("Basket", Icons.Rounded.ShoppingCart, Route.BASKET),
                                BottomBarItem("Orders", Icons.Rounded.List, Route.ORDERS)
                            ),
                            onNavigate = navController::navigate,
                            navController = navController,
                            isBottomBarOverlapped = isBottomBarOverlapped.value,
                            modifier = Modifier.offset { IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt()) }
                        )
                    },
                    floatingActionButton = {
                        FloatingButton(
                            modalBottomSheetState = bottomSheetState,
                            navController = navController,
                            isBottomBarOverlapped = isBottomBarOverlapped
                        )
                    }
                ) {
                    NavHost(navController = navController, startDestination = Route.SHOPS_OVERVIEW) {
                        composable(route = Route.LOGIN) {
                            LoginScreen(onNavigate = navController::navigate)
                        }
                        composable(route = Route.SIGNUP) {
                            SignupScreen(onNavigate = navController::navigate)
                        }
                        composable(route = Route.PRODUCTS_OVERVIEW) {
                            ProductOverviewScreen(
                                onNavigate = navController::navigate,
                                modalBottomSheetState = bottomSheetState,
                                topBarOffset = topBarOffsetHeightPx.value
                            )
                        }
                        composable(route = "${Route.PRODUCT}/{productId}", arguments =
                        listOf(navArgument("productId") { type = NavType.StringType})) {
                            ProductScreen(
                                onNavigate = navController::navigate,
                                onNavigateBack = navController::navigateBack
                            )
                        }
                        composable(route = Route.SHOPS_OVERVIEW) {
                            ShopOverviewScreen(onNavigate = navController::navigate, topBarOffset = topBarOffsetHeightPx.value)
                        }
                        composable(route = "${Route.SHOP}/{shopId}", arguments =
                        listOf(navArgument("shopId") { type = NavType.StringType})) {
                            ShopScreen(
                                onNavigate = navController::navigate,
                                onNavigateBack = navController::navigateBack
                            )
                        }
                        composable(route = Route.BASKET) {
                            Text(text = "BASKET SCREEN")
                        }
                        composable(route = Route.ORDERS) {
                            Text(text = "ORDERS SCREEN")
                        }
                    }
                }
            }
        }
    }
}

