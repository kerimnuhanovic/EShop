package com.eshop.coreui.util

fun checkIsProductRoute(route: String?) = route == "product/{productId}"
fun checkIsShopRoute(route: String?) = route == "shop/{shopId}"