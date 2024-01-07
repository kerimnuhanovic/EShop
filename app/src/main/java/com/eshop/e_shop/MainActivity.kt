package com.eshop.e_shop

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eshop.core.navigation.Route
import com.eshop.coreui.components.FloatingButton
import com.eshop.coreui.theme.EShopTheme
import com.eshop.e_shop.components.BottomBar
import com.eshop.e_shop.navigation.navigate
import com.eshop.e_shop.util.BottomBarItem
import com.eshop.login_presentation.login.LoginScreen
import com.eshop.productoverview_presentation.ProductOverviewScreen
import com.eshop.signup_presentation.signup.SignupScreen
import dagger.hilt.android.AndroidEntryPoint

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
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomBar(
                            items = listOf(
                                BottomBarItem("Products", Icons.Default.Home, Route.PRODUCTS),
                                BottomBarItem("Shops", Icons.Default.Place, Route.SHOPS),
                                BottomBarItem("Basket", Icons.Default.ShoppingCart, Route.BASKET),
                                BottomBarItem("Orders", Icons.Default.List, Route.ORDERS)
                            ),
                            onNavigate = navController::navigate,
                            navController = navController,
                            isBottomBarOverlapped = isBottomBarOverlapped.value
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
                    NavHost(navController = navController, startDestination = Route.LOGIN) {
                        composable(route = Route.LOGIN) {
                            LoginScreen(onNavigate = navController::navigate)
                        }
                        composable(route = Route.SIGNUP) {
                            SignupScreen(onNavigate = navController::navigate)
                        }
                        composable(route = Route.PRODUCTS) {
                            ProductOverviewScreen(
                                onNavigate = navController::navigate,
                                modalBottomSheetState = bottomSheetState
                            )
                        }
                        composable(route = Route.SHOPS) {
                            Text(text = "SHOPS SCREEN")
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

