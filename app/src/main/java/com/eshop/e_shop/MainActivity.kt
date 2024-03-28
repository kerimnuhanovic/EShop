package com.eshop.e_shop

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.theme.EShopTheme
import com.eshop.e_shop.navigation.navigate
import com.eshop.e_shop.navigation.navigateBack
import com.eshop.login_presentation.login.LoginScreen
import com.eshop.product_presentation.ProductScreen
import com.eshop.productoverview_presentation.ProductOverviewScreen
import com.eshop.shop_presentation.ShopScreen
import com.eshop.shopoverview_presentation.ShopOverviewScreen
import com.eshop.signup_presentation.signup.SignupScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EShopTheme {
                val navController = rememberNavController()
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

