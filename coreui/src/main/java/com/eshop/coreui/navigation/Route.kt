package com.eshop.coreui.navigation

object Route {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val MAIN = "main"
    const val PRODUCTS_OVERVIEW = "products_overview"
    const val PRODUCT = "product"
    const val BASKET = "basket"
    const val ORDERS = "orders"
    const val SHOPS_OVERVIEW = "shops_overview"
    const val SHOP = "shop"
    fun listLandingRoutes(): List<String> =
        listOf(
            LOGIN,
            SIGNUP
        )
}