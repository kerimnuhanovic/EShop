package com.eshop.e_shop

import com.eshop.coreui.navigation.Route

data class AppState(
    val isUserLoggedIn: Boolean = false,
    val startDestination: String = Route.LOGIN
)
