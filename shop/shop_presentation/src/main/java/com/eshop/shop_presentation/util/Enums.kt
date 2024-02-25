package com.eshop.shop_presentation.util

import java.lang.IllegalArgumentException

sealed class ShopLayout(val value: String) {
    object Products : ShopLayout("Products")
    object Reviews : ShopLayout("Reviews")
    companion object {
        fun fromString(layout: String): ShopLayout {
            return when (layout) {
                "Products" -> Products
                "Reviews" -> Reviews
                else -> throw IllegalArgumentException("Function ShopLayout.toString() called with invalid arguments!")
            }
        }
    }
}