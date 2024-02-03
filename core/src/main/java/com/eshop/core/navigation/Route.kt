package com.eshop.core.navigation

object Route {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val MAIN = "main"
    const val PRODUCTS_OVERVIEW = "products_overview"
    const val PRODUCT = "product"
    const val SHOPS = "shops"
    const val BASKET = "basket"
    const val ORDERS = "orders"
    fun listLandingRoutes(): List<String> =
        listOf(
            LOGIN,
            SIGNUP
        )
}