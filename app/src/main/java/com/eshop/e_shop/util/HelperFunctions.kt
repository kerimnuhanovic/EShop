package com.eshop.e_shop.util

fun checkIsProductRoute(route: String?) = route == "product/{productId}"
fun checkIsShopRoute(route: String?) = route == "shop/{shopId}"