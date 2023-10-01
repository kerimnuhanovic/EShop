package com.eshop.e_shop

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eshop.core.navigation.Route
import com.eshop.coreui.theme.EShopTheme
import com.eshop.e_shop.navigation.navigate
import com.eshop.login_presentation.login.LoginScreen
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
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(navController = navController, startDestination = Route.LOGIN) {
                        composable(route = Route.LOGIN) {
                            LoginScreen(onNavigate = navController::navigate)
                        }
                        composable(route = Route.MAIN) {
                            Text(text = "MAIN SCREEN")
                        }
                        composable(route = Route.SIGNUP) {
                            SignupScreen(onNavigate = navController::navigate)
                        }
                    }
                }
            }
        }
    }
}

