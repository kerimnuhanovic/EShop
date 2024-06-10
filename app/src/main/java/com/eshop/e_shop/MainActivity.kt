package com.eshop.e_shop

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eshop.cart_presentation.CartScreen
import com.eshop.chat_presentation.chat.ChatScreen
import com.eshop.chat_presentation.conversations.ConversationsScreen
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.theme.EShopTheme
import com.eshop.e_shop.navigation.navigate
import com.eshop.e_shop.navigation.navigateBack
import com.eshop.favouriteproducts_presentation.FavouriteProductsScreen
import com.eshop.favouriteshops_presentation.FavouriteShopsScreen
import com.eshop.login_presentation.login.LoginScreen
import com.eshop.orders_presentation.OrdersScreen
import com.eshop.product_presentation.ProductScreen
import com.eshop.productoverview_presentation.ProductOverviewScreen
import com.eshop.shop_presentation.ShopScreen
import com.eshop.shopoverview_presentation.ShopOverviewScreen
import com.eshop.signup_presentation.signup.SignupScreen
import com.eshop.userdashboard_presentation.UserDashboardScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var keepSplashScreenOpened = true
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreenOpened
        }
        setContent {
            EShopTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = hiltViewModel()
                val appState = mainViewModel.state.collectAsState().value
                NavHost(navController = navController, startDestination = appState.startDestination) {
                    composable(route = Route.LOGIN) {
                        LoginScreen(onNavigate = navController::navigate, onDataLoaded = {
                            keepSplashScreenOpened = false
                        })
                    }
                    composable(route = Route.SIGNUP) {
                        SignupScreen(onNavigate = navController::navigate)
                    }
                    composable(route = Route.PRODUCTS_OVERVIEW) {
                        ProductOverviewScreen(
                            onNavigate = navController::navigate,
                            onDataLoaded = {
                                keepSplashScreenOpened = false
                            }
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
                        ShopOverviewScreen(onNavigate = navController::navigate)
                    }
                    composable(route = "${Route.SHOP}/{shopId}", arguments =
                    listOf(navArgument("shopId") { type = NavType.StringType})) {
                        ShopScreen(
                            onNavigate = navController::navigate,
                            onNavigateBack = navController::navigateBack
                        )
                    }
                    composable(route = Route.BASKET) {
                        CartScreen(onNavigate = navController::navigate)
                    }
                    composable(route = Route.ORDERS) {
                        OrdersScreen(onNavigate = navController::navigate, onNavigateBack = navController::navigateBack)
                    }
                    composable(route = Route.CONVERSATIONS) {
                        ConversationsScreen(
                            onNavigate = navController::navigate
                        )
                    }
                    composable(route = "${Route.CHAT}/{chatPartner}", arguments = listOf(navArgument("chatPartner") {type = NavType.StringType})) {
                        ChatScreen(onNavigate = navController::navigate, onNavigateBack = navController::navigateBack)
                    }
                    composable(route = Route.DASHBOARD) {
                        UserDashboardScreen(onNavigate = navController::navigate)
                    }
                    composable(route = Route.FAVOURITE_PRODUCTS) {
                        FavouriteProductsScreen(onNavigate = navController::navigate, onNavigateBack = navController::navigateBack)
                    }
                    composable(route = Route.FAVOURITE_SHOPS) {
                        FavouriteShopsScreen(onNavigate = navController::navigate, onNavigateBack = navController::navigateBack)
                    }
                }
            }
        }
    }
}

